package com.misbah.todo.core.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.misbah.todo.core.data.model.Task
import com.misbah.todo.core.di.module.ApplicationScope
import com.nytimes.utils.AppEnums
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Wash the dishes","Wash the dishes"))
                dao.insert(Task("Do the laundry", "Do the laundry"))
                dao.insert(Task("Buy groceries","Buy groceries", important = AppEnums.TasksPriority.High.value))
                dao.insert(Task("Prepare food", "Prepare food", important =  AppEnums.TasksPriority.Medium.value))
                dao.insert(Task("Call mom", "Calling mom"))
                dao.insert(Task("Visit grandma","Visiting native place to meet grandma",  completed = true))
                dao.insert(Task("Repair my bike","Repair and servicing of Bike"))
                dao.insert(Task("Call Elon Musk","Calling Elon Musk"))
            }
        }
    }
}