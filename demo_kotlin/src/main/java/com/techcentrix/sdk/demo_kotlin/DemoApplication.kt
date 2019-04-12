package com.techcentrix.sdk.demo_kotlin

import android.app.Application
import com.techcentrix.sdk.SDKConfig
import com.techcentrix.sdk.TechCentrixSDK

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        TechCentrixSDK.init(SDKConfig.Builder(this, BuildConfig.MOBILE_API_KEY).build())
    }

}