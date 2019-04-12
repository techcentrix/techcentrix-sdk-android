package com.techcentrix.sdk.demo_java.util;

import androidx.annotation.Nullable;

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
public class Event<T> {

    private boolean hasBeenHandled = false;

    @Nullable
    private final T content;

    public Event(@Nullable T content) {
        this.content = content;
    }

    /**
     * Returns the content and prevents its use again.
     */
    @Nullable
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return content;
        }
    }
}
