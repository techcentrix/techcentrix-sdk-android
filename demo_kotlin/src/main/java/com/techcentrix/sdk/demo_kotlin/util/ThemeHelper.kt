package com.techcentrix.sdk.demo_kotlin.util

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import java.util.*

object ThemeHelper {

    const val PREFS_KEY = "pref_key_theme"

    private const val TAG = "ThemeHelper"

    private const val THEME_LIGHT = "LIGHT"
    private const val THEME_DARK = "DARK"
    private const val THEME_DEFAULT = "DEFAULT"

    fun applyThemeFromPreferenceValue(themePref: String) {
        applyTheme(themePref.toUpperCase(Locale.ROOT))
    }

    fun applyThemeFromSharedPreferences(context: Context) {
        applyTheme(loadThemeFromSharedPreferences(context))
    }

    private fun loadThemeFromSharedPreferences(context: Context): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return requireNotNull(sharedPreferences.getString(PREFS_KEY, THEME_DEFAULT)).toUpperCase(
            Locale.ROOT
        )
    }

    private fun applyTheme(theme: String) {
        when (theme) {
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> {
                if (Build.VERSION.SDK_INT >= 29) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }

}