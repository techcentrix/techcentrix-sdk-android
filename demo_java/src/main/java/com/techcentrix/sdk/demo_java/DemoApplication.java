package com.techcentrix.sdk.demo_java;

import android.app.Application;

import com.techcentrix.sdk.demo_java.util.ThemeHelper;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // TechCentrixSDK.setLoggingEnabled(true);
        ThemeHelper.applyThemeFromSharedPreferences(this);
    }
}
