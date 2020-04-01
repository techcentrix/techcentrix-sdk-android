package com.techcentrix.sdk.demo_kotlin.ui

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.techcentrix.sdk.demo_kotlin.R
import com.techcentrix.sdk.demo_kotlin.util.ThemeHelper

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        const val TAG = "SettingsFragment"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val themePreference: ListPreference = requireNotNull(findPreference(ThemeHelper.PREFS_KEY))
        themePreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                ThemeHelper.applyThemeFromPreferenceValue(newValue as String)
                true
            }
    }
}
