package com.misbah.todo.core.di.builder


import com.misbah.todo.ui.addedittask.AddEditFragmentModule
import com.misbah.todo.ui.addedittask.AddEditTaskFragment
import com.misbah.todo.ui.dialogs.ConfirmationDialogFragment
import com.misbah.todo.ui.dialogs.DialogFragmentModule
import com.misbah.todo.ui.dialogs.QuitDialogFragment
import com.misbah.todo.ui.tasks.TasksFragment
import com.misbah.todo.ui.tasks.TasksFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author: Mohammad Misbah
 * @since: 26-Sep-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [TasksFragmentModule::class])
    abstract fun contributeTasksFragment() : TasksFragment

    @ContributesAndroidInjector(modules = [AddEditFragmentModule::class])
    abstract fun contributeAddEditTaskFragment() : AddEditTaskFragment

    @ContributesAndroidInjector(modules = [DialogFragmentModule::class])
    abstract fun contributeDialogFragment() : QuitDialogFragment

    @ContributesAndroidInjector(modules = [DialogFragmentModule::class])
    abstract fun contributeQuitDialogFragment() : ConfirmationDialogFragment

}