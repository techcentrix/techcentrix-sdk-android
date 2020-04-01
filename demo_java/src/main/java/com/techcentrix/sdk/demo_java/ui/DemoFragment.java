package com.techcentrix.sdk.demo_java.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.techcentrix.sdk.demo_java.R;
import com.techcentrix.sdk.demo_java.util.EventObserver;
import com.techcentrix.sdk.ui.TechCentrixActivity;

public class DemoFragment extends Fragment {

    public DemoFragment() {
        super(R.layout.fragment_demo);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressBar actionBarProgressBar = view.findViewById(R.id.actionBarProgressBar);
        Button launchSDKButton = view.findViewById(R.id.launchSDKButton);

        DemoViewModel viewModel = new ViewModelProvider(this).get(DemoViewModel.class);

        viewModel.getViewState().observe(getViewLifecycleOwner(), it -> {
            actionBarProgressBar.setVisibility(it.isProgressBarVisible() ? View.VISIBLE : View.GONE);
            launchSDKButton.setEnabled(it.isLaunchButtonEnabled());
        });

        viewModel.getViewCommand().observe(getViewLifecycleOwner(), new EventObserver<>(it -> {
            switch (it) {
                case LAUNCH_SDK:
                    TechCentrixActivity.start(view.getContext());
                    break;

                case SHOW_ERROR:
                    Snackbar.make(view, R.string.oops_something_went_wrong_try_again, Snackbar.LENGTH_LONG).show();
                    break;
            }
        }));

        launchSDKButton.setOnClickListener(it -> viewModel.launchSDK());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_demo, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            showSettings();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showSettings() {
        FragmentManager fm = getParentFragmentManager();
        if (fm.findFragmentByTag(SettingsFragment.TAG) == null) {
            fm.beginTransaction()
                    .setCustomAnimations(R.animator.short_fade_in, R.animator.short_fade_out, R.animator.short_fade_in,
                            R.animator.short_fade_out)
                    .replace(R.id.fragmentContainerView, new SettingsFragment(), SettingsFragment.TAG)
                    .addToBackStack(null).commit();
        }
    }

}
