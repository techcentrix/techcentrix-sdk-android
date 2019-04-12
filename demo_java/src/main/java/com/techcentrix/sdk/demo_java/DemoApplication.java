package com.techcentrix.sdk.demo_java;

import android.app.Application;

import com.techcentrix.sdk.SDKConfig;
import com.techcentrix.sdk.TechCentrixSDK;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TechCentrixSDK.init(new SDKConfig.Builder(this, BuildConfig.MOBILE_API_KEY).build());
    }
}
