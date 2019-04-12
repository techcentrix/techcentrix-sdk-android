package com.techcentrix.sdk.demo_java.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.techcentrix.sdk.demo_java.R;
import com.techcentrix.sdk.demo_java.util.EventObserver;
import com.techcentrix.sdk.ui.TechCentrixActivity;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);

        ProgressBar actionBarProgressBar = findViewById(R.id.actionBarProgressBar);
        Button launchSDKButton = findViewById(R.id.launchSDKButton);

        DemoViewModel viewModel = ViewModelProviders.of(this).get(DemoViewModel.class);

        viewModel.getViewState().observe(this, it -> {
            actionBarProgressBar.setVisibility(it.isProgressBarVisible() ? View.VISIBLE : View.INVISIBLE);
            launchSDKButton.setEnabled(it.isLaunchButtonEnabled());
        });

        viewModel.getViewCommand().observe(this, new EventObserver<>(it -> {
            switch (it) {
                case LAUNCH_SDK:
                    TechCentrixActivity.start(this);
                    break;

                case SHOW_ERROR:
                    Snackbar.make(findViewById(R.id.demoActivityLayout), R.string.oops_something_went_wrong_try_again,
                            Snackbar.LENGTH_LONG).show();
                    break;
            }
        }));

        launchSDKButton.setOnClickListener(it -> viewModel.launchSDK());
    }
}
