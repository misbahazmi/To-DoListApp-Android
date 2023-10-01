package com.misbah.todo.ui.main


import androidx.lifecycle.ViewModel
import com.misbah.todo.ui.tasks.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: MOHAMMAD MISBAH
 * @since: 16-Jul-2022
 * @sample: Technology Assessment for Android Role
 * @desc MainView Model
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java||Flutter
 */
class MainViewModel @Inject constructor() : ViewModel(){
    private val tasksEventChannel = Channel<TasksViewModel.TasksEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()
    fun onAddNewTaskClick() = CoroutineScope(Dispatchers.IO).launch {
        tasksEventChannel.send(TasksViewModel.TasksEvent.NavigateToAddTaskScreen)
    }
}