package com.misbah.todo.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.misbah.todo.R
import com.misbah.todo.core.base.BaseActivity
import com.misbah.todo.databinding.ActivitySpalshBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<MainViewModel>() {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    private lateinit var binding: ActivitySpalshBinding
    @Inject
    internal lateinit var viewModel: MainViewModel
    override fun getViewModel(): MainViewModel {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(binding.imgToDo.context)
            .setDefaultRequestOptions(RequestOptions())
            .load(R.drawable.todo_list)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(R.drawable.todo_list)
            .into(binding.imgToDo)

        activityScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

}