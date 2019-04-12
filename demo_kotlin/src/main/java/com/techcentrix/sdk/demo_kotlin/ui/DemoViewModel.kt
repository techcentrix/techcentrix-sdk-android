package com.techcentrix.sdk.demo_kotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techcentrix.sdk.TechCentrixSDK
import com.techcentrix.sdk.demo_kotlin.BuildConfig
import com.techcentrix.sdk.demo_kotlin.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DemoViewModel : ViewModel(), CoroutineScope {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    private val _viewCommand = MutableLiveData<Event<ViewCommand>>()
    val viewCommand: LiveData<Event<ViewCommand>>
        get() = _viewCommand

    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    fun launchSDK() = launch {
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

    override fun onCleared() {
        super.onCleared()

        job.cancel()
    }

    data class ViewState(val progressBarVisible: Boolean, val launchButtonEnabled: Boolean)

    sealed class ViewCommand {

        object LaunchSDK : ViewCommand()

        object ShowError : ViewCommand()
    }

}