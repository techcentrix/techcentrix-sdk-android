package com.techcentrix.sdk.demo_kotlin

import android.app.Application
import com.techcentrix.sdk.demo_kotlin.util.ThemeHelper

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // TechCentrixSDK.setLoggingEnabled(true)
        ThemeHelper.applyThemeFromSharedPreferences(this)
    }

}