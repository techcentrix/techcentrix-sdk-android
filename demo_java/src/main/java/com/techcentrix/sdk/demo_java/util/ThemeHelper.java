package com.techcentrix.sdk.demo_java.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ThemeHelper {

    public static final String PREFS_KEY = "pref_key_theme";

    private static final String TAG = "ThemeHelper";

    private static final String THEME_LIGHT = "LIGHT";
    private static final String THEME_DARK = "DARK";
    private static final String THEME_DEFAULT = "DEFAULT";

    public static void applyThemeFromPreferenceValue(@NotNull String themePref) {
        applyTheme(themePref.toUpperCase(Locale.ROOT));
    }

    public static void applyThemeFromSharedPreferences(@NotNull Context context) {
        applyTheme(loadThemeFromSharedPreferences(context));
    }

    @NotNull
    private static String loadThemeFromSharedPreferences(@NotNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(PREFS_KEY, THEME_DEFAULT).toUpperCase(Locale.ROOT);
    }

    private static void applyTheme(@NotNull String theme) {
        switch (theme) {
            case THEME_LIGHT: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            }
            case THEME_DARK: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            }
            default: {
                if (Build.VERSION.SDK_INT >= 29) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }
                break;
            }
        }
    }

}
