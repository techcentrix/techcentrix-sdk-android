package com.techcentrix.sdk.demo_kotlin.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, crossinline observer: (T) -> Unit) =
    this.observe(owner, Observer { observer(it!!) })
