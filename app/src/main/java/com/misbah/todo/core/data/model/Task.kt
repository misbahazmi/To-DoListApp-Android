package com.misbah.todo.core.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nytimes.utils.AppEnums
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Entity(tableName = "task_table")
@Parcelize
data class Task(
    val title: String,
    val name: String,
    val category: Int = AppEnums.TasksCategory.All.value,
    val important: Int = AppEnums.TasksPriority.Normal.value,
    val completed: Boolean = false,
    val due: Long = System.currentTimeMillis(),
    val created: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(created)
    val dueDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(due)
}