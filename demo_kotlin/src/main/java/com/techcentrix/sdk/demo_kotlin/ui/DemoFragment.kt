package com.techcentrix.sdk.demo_kotlin.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.techcentrix.sdk.demo_kotlin.R
import com.techcentrix.sdk.demo_kotlin.util.EventObserver
import com.techcentrix.sdk.demo_kotlin.util.observeNotNull
import com.techcentrix.sdk.ui.TechCentrixActivity
import kotlinx.android.synthetic.main.fragment_demo.*

class DemoFragment : Fragment(R.layout.fragment_demo) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(DemoViewModel::class.java)

        viewModel.viewState.observeNotNull(viewLifecycleOwner) {
            actionBarProgressBar.isVisible = it.progressBarVisible
            launchSDKButton.isEnabled = it.launchButtonEnabled
        }

        viewModel.viewCommand.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is DemoViewModel.ViewCommand.LaunchSDK -> TechCentrixActivity.start(view.context)

                is DemoViewModel.ViewCommand.ShowError -> {
                    Snackbar.make(
                        view,
                        R.string.oops_something_went_wrong_try_again,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        launchSDKButton.setOnClickListener { viewModel.launchSDK() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_demo, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == R.id.menu_settings) {
            showSettings()
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    private fun showSettings() = with(parentFragmentManager) {
        if (findFragmentByTag(SettingsFragment.TAG) == null) {
            beginTransaction()
                .setCustomAnimations(
                    R.animator.short_fade_in, R.animator.short_fade_out,
                    R.animator.short_fade_in, R.animator.short_fade_out
                )
                .replace(R.id.fragmentContainerView, SettingsFragment(), SettingsFragment.TAG)
                .addToBackStack(null)
                .commit()
        }
    }

}