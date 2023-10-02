package com.misbah.todo.notifications
import android.content.Context
import com.misbah.todo.core.data.storage.TaskDao
import com.misbah.todo.ui.dialogs.DialogRepository
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author: Mohammad Misbah
 * @since: 26-Sep-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */

open class TaskDataRepository @Inject constructor(private val taskDao : TaskDao){
    fun deleteAllCompletedTasks(){
        taskDao.deleteCompletedTasks()
    }

}