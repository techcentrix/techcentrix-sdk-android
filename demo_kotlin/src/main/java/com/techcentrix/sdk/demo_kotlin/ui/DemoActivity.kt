package com.techcentrix.sdk.demo_kotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.techcentrix.sdk.demo_kotlin.R
import com.techcentrix.sdk.demo_kotlin.util.EventObserver
import com.techcentrix.sdk.demo_kotlin.util.observeNotNull
import com.techcentrix.sdk.ui.TechCentrixActivity
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_demo)

        val viewModel = ViewModelProviders.of(this).get(DemoViewModel::class.java)

        viewModel.viewState.observeNotNull(this) {
            actionBarProgressBar.isInvisible = !it.progressBarVisible
            launchSDKButton.isEnabled = it.launchButtonEnabled
        }

        viewModel.viewCommand.observe(this, EventObserver {
            when (it) {
                is DemoViewModel.ViewCommand.LaunchSDK -> TechCentrixActivity.start(this)

                is DemoViewModel.ViewCommand.ShowError -> Snackbar.make(
                    demoActivityLayout,
                    R.string.oops_something_went_wrong_try_again,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })

        launchSDKButton.setOnClickListener { viewModel.launchSDK() }
    }
}
