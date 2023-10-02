package com.misbah.todo.ui.main

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.animation.AnimationUtils
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.misbah.todo.R
import com.misbah.todo.core.base.BaseActivity
import com.misbah.todo.databinding.ActivityMainBinding
import com.misbah.todo.notifications.NotificationReminderReceiver
import com.misbah.todo.ui.tasks.TasksFragmentDirections
import com.misbah.todo.ui.tasks.TasksViewModel
import com.misbah.todo.ui.utils.Constants
import com.misbah.todo.ui.utils.exhaustive
import kotlinx.coroutines.launch
import java.text.DateFormat
import javax.inject.Inject


class MainActivity : BaseActivity<MainViewModel>() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    @Inject
    internal lateinit var viewModel: MainViewModel
    override fun getViewModel(): MainViewModel {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            viewModel.onAddNewTaskClick()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.tasksFragment, R.id.nav_settings, R.id.nav_category
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.tasksEvent.collect { event ->
                    when (event) {
                        is TasksViewModel.TasksEvent.NavigateToAddTaskScreen -> {
                            val action =
                                TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                                    "New Task",
                                    null
                                )
                            navController.navigate(action)

                        }
                        is TasksViewModel.TasksEvent.NavigateToEditTaskScreen -> {
                            val action =
                                TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(
                                    "Edit Tasks",
                                    event.task
                                )
                            navController.navigate(action)
                        }
                        is TasksViewModel.TasksEvent.QuitAppPopUp -> {
                            val action =
                                TasksFragmentDirections.actionGlobalQuitAppDialogFragment()
                            navController.navigate(action)
                        }
                        else ->{}
                    }.exhaustive
                }
            }
        }
        binding.appBarMain.fab.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_anim))

        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                exitOnBackPressed()
            }
        } else {
            onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        Log.i("TAG", "handleOnBackPressed: Exit")
                        exitOnBackPressed()
                    }
                })
        }
        navView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawers()   // close drawer when item is tapped
            val bundle = bundleOf("title" to "New Task", "task" to null)
            when (menuItem.itemId) {
                R.id.addEditTaskFragment -> {
                    navController.navigate(menuItem.itemId, bundle)
                }
                else -> {
                    navController.navigate(menuItem.itemId)
                }
            }
            true
        }
        //scheduleTaskReminder()
        createNotificationChannel()
        requestNotificationPermission()
    }

    private fun scheduleTaskReminder() {
        val intentAlarm = Intent(this, NotificationReminderReceiver::class.java)
        println("calling Alarm receiver ")
        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
        //set the notification to repeat every fifteen minutes
        //set the notification to repeat every fifteen minutes
        val startTime = (1 * 60 * 1000).toLong() // 2 min

        // set unique id to the pending item, so we can call it when needed
        val pi = PendingIntent.getBroadcast(
            this,
            1100,
            intentAlarm,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setInexactRepeating(
            AlarmManager.RTC, SystemClock.elapsedRealtime() +
                    startTime, (60 * 1000).toLong(), pi
        )
        //alarmManager.cancel(pi)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun hideFAB(){
        binding.appBarMain.fab.hide()
    }
    fun showFAB(){
        binding.appBarMain.fab.show()
    }
    fun exitOnBackPressed() {
        if (navController.currentDestination?.id == R.id.tasksFragment)
            viewModel.onBackClickQuitApp()
        else
            navController.popBackStack()
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean -> }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) != PermissionChecker.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(POST_NOTIFICATIONS)
            }
        }
    }


}
const val ADD_TASK_RESULT_OK = Activity.RESULT_FIRST_USER
const val EDIT_TASK_RESULT_OK = Activity.RESULT_FIRST_USER + 1