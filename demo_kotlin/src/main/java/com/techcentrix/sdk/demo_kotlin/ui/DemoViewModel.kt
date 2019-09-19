package com.techcentrix.sdk.demo_kotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcentrix.sdk.TechCentrixSDK
import com.techcentrix.sdk.demo_kotlin.BuildConfig
import com.techcentrix.sdk.demo_kotlin.util.Event
import kotlinx.coroutines.launch

class DemoViewModel : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    private val _viewCommand = MutableLiveData<Event<ViewCommand>>()
    val viewCommand: LiveData<Event<ViewCommand>>
        get() = _viewCommand

    fun launchSDK() = viewModelScope.launch {
        if (TechCentrixSDK.isSignedIn()) {
            _viewCommand.value = Event(ViewCommand.LaunchSDK)
        } else {
            _viewState.value = ViewState(progressBarVisible = true, launchButtonEnabled = false)
            val signInResult = TechCentrixSDK.signIn(BuildConfig.ONE_TIME_TOKEN)
            _viewState.value = ViewState(progressBarVisible = false, launchButtonEnabled = true)

            if (signInResult) {
                _viewCommand.value = Event(ViewCommand.LaunchSDK)
            } else {
                _viewCommand.value = Event(ViewCommand.ShowError)
            }
        }
    }

    data class ViewState(val progressBarVisible: Boolean, val launchButtonEnabled: Boolean)

    sealed class ViewCommand {

        object LaunchSDK : ViewCommand()

        object ShowError : ViewCommand()
    }

}