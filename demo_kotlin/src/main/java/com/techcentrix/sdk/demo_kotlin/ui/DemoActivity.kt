package com.techcentrix.sdk.demo_kotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.techcentrix.sdk.demo_kotlin.R
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity(R.layout.activity_demo) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(actionBarToolbar)
    }
}
