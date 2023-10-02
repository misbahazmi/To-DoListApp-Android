package com.misbah.todo.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.misbah.todo.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }
}