package com.misbah.todo.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.misbah.todo.core.data.storage.PreferencesManager
import javax.inject.Inject

class SettingsViewModel  @Inject constructor(preferencesManager: PreferencesManager) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
}