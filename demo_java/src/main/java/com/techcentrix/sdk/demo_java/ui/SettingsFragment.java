package com.techcentrix.sdk.demo_java.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.techcentrix.sdk.demo_java.R;
import com.techcentrix.sdk.demo_java.util.ThemeHelper;

public class SettingsFragment extends PreferenceFragmentCompat {

    static final String TAG = "SettingsFragment";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        @Nullable ListPreference themePreference = findPreference(ThemeHelper.PREFS_KEY);
        if (themePreference != null) {
            themePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                ThemeHelper.applyThemeFromPreferenceValue((String) newValue);
                return true;
            });
        }
    }
}
