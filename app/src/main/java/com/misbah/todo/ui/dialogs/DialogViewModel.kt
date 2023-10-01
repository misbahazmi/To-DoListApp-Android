package com.misbah.todo.ui.dialogs

import androidx.lifecycle.ViewModel
import com.misbah.todo.core.data.model.Task
import com.misbah.todo.ui.addedittask.AddEditTaskViewModel
import com.misbah.todo.ui.main.ADD_TASK_RESULT_OK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: Mohammad Misbah
 * @since: 26-Sep-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class DialogViewModel @Inject constructor(private val repository: DialogRepository) : ViewModel() {
    fun onConfirmDeleteAllClick() =  CoroutineScope(Dispatchers.IO).launch {
        repository.deleteAllCompletedTasks()
    }
}