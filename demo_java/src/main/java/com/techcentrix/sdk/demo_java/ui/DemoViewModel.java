package com.techcentrix.sdk.demo_java.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techcentrix.sdk.TechCentrixSDK;
import com.techcentrix.sdk.demo_java.BuildConfig;
import com.techcentrix.sdk.demo_java.util.Event;

import java.util.concurrent.Future;

@SuppressWarnings("WeakerAccess")
public class DemoViewModel extends ViewModel {

    private Future<Boolean> signInFuture = null;

    private final MutableLiveData<ViewState> _viewState = new MutableLiveData<>();

    LiveData<ViewState> getViewState() {
        return _viewState;
    }

    private final MutableLiveData<Event<ViewCommand>> _viewCommand = new MutableLiveData<>();

    LiveData<Event<ViewCommand>> getViewCommand() {
        return _viewCommand;
    }

    void launchSDK() {
        if (TechCentrixSDK.isSignedIn()) {
            _viewCommand.setValue(new Event<>(ViewCommand.LAUNCH_SDK));
        } else {
            _viewState.setValue(new ViewState(true, false));

            signInFuture = TechCentrixSDK.signInAsync(BuildConfig.ONE_TIME_TOKEN, future -> {
                try {
                    final boolean signInResult = future.get();

                    if (signInResult) {
                        _viewCommand.postValue(new Event<>(ViewCommand.LAUNCH_SDK));
                    } else {
                        _viewCommand.postValue(new Event<>(ViewCommand.SHOW_ERROR));
                    }
                } catch (Exception ex) {
                    _viewCommand.postValue(new Event<>(ViewCommand.SHOW_ERROR));
                } finally {
                    _viewState.postValue(new ViewState(false, true));
                }
            });
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (signInFuture != null) {
            signInFuture.cancel(true);
            signInFuture = null;
        }
    }

    class ViewState {

        private final boolean progressBarVisible;
        private final boolean launchButtonEnabled;

        ViewState(boolean progressBarVisible, boolean launchButtonEnabled) {
            this.progressBarVisible = progressBarVisible;
            this.launchButtonEnabled = launchButtonEnabled;
        }

        public boolean isProgressBarVisible() {
            return progressBarVisible;
        }

        public boolean isLaunchButtonEnabled() {
            return launchButtonEnabled;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ViewState viewState = (ViewState) o;

            if (progressBarVisible != viewState.progressBarVisible) return false;
            return launchButtonEnabled == viewState.launchButtonEnabled;
        }

        @Override
        public int hashCode() {
            int result = (progressBarVisible ? 1 : 0);
            result = 31 * result + (launchButtonEnabled ? 1 : 0);
            return result;
        }
    }

    enum ViewCommand {
        LAUNCH_SDK, SHOW_ERROR
    }

}
