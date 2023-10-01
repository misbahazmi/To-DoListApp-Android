package com.misbah.todo.ui.main

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
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
import com.misbah.todo.ui.tasks.TasksFragmentDirections
import com.misbah.todo.ui.tasks.TasksViewModel
import com.misbah.todo.ui.utils.exhaustive
import com.misbah.todo.ui.utils.gone
import com.misbah.todo.ui.utils.invisible
import com.misbah.todo.ui.utils.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
                R.id.tasksFragment, R.id.nav_gallery, R.id.nav_slideshow
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
                        else ->{}
                    }.exhaustive
                }
            }
        }
        binding.appBarMain.fab.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_anim))

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

}
const val ADD_TASK_RESULT_OK = Activity.RESULT_FIRST_USER
const val EDIT_TASK_RESULT_OK = Activity.RESULT_FIRST_USER + 1