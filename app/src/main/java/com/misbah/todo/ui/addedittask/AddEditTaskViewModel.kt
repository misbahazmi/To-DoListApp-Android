package com.misbah.todo.ui.addedittask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misbah.todo.core.data.model.Category
import com.misbah.todo.core.data.model.Task
import com.misbah.todo.core.data.storage.TaskDao
import com.misbah.todo.ui.main.ADD_TASK_RESULT_OK
import com.misbah.todo.ui.main.EDIT_TASK_RESULT_OK
import com.misbah.todo.ui.tasks.TasksViewModel
import com.nytimes.utils.AppEnums
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditTaskViewModel @Inject constructor(
    private val taskDao: TaskDao,
    private val state : SavedStateHandle
) : ViewModel() {

    var task = MutableLiveData<Task>()

    var selectedDateTime = task.value?.due ?: System.currentTimeMillis()
    var taskTitle = task.value?.title ?: ""
    var taskDescription = task.value?.name ?: ""
    var taskImportance = task.value?.important ?: 0
    var tasksCategory = task.value?.category ?: 0

    var dueDate = selectedDateTime


    private val addEditTaskEventChannel = Channel<AddEditTaskEvent>()
    val addEditTaskEvent = addEditTaskEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (taskTitle.isBlank()) {
            showInvalidInputMessage("Title cannot be empty")
            return
        }
        if (taskDescription.isBlank()) {
            showInvalidInputMessage("Description cannot be empty")
            return
        }
        if (task.value != null) {
            val updatedTask =  task.value!!.copy(name = taskDescription, title = taskTitle , important = taskImportance, category = tasksCategory, due = dueDate)
            updateTask(updatedTask)
        } else {
            val newTask = Task(name = taskDescription, title = taskTitle, important = taskImportance, category = tasksCategory, due = dueDate)
            createTask(newTask)
        }
    }

    fun showDatePicker(){
        showDateTimePicker()
    }

    fun onDateTimeResult(result: Long) {
        dateTimeWithResult(result)
    }

    private fun createTask(task: Task) =  CoroutineScope(Dispatchers.IO).launch {
        taskDao.insert(task)
        addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(ADD_TASK_RESULT_OK))
    }

    private fun updateTask(task: Task) =  CoroutineScope(Dispatchers.IO).launch {
        taskDao.update(task)
        addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditTaskEventChannel.send(AddEditTaskEvent.ShowInvalidInputMessage(text))
    }

    private fun dateTimeWithResult(text: Long) = viewModelScope.launch {
        addEditTaskEventChannel.send(AddEditTaskEvent.DateTimeWithResult(text))
    }

    private fun showDateTimePicker() = viewModelScope.launch {
        addEditTaskEventChannel.send(AddEditTaskEvent.ShowDateTimePicker)
    }

    sealed class AddEditTaskEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditTaskEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditTaskEvent()
        data object ShowDateTimePicker : AddEditTaskEvent()
        data class DateTimeWithResult(val result: Long) : AddEditTaskEvent()
    }
}