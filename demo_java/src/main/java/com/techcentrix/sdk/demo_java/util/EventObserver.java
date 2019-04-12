package com.techcentrix.sdk.demo_java.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 * <p>
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
public class EventObserver<T> implements Observer<Event<T>> {

    public interface Handler<T> {
        void handle(T content);
    }

    public EventObserver(@NonNull Handler<T> onEventUnhandledContent) {
        this.onEventUnhandledContent = onEventUnhandledContent;
    }

    private final Handler<T> onEventUnhandledContent;

    @Override
    public void onChanged(@Nullable Event<T> event) {
        if (event != null) {
            final T content = event.getContentIfNotHandled();
            if (content != null) {
                onEventUnhandledContent.handle(content);
            }
        }
    }
}
