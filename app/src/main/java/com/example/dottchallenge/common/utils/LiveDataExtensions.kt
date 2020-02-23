package com.example.dottchallenge.common.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (data: T) -> Unit) {
    this.observe(owner, Observer {
        if (it != null) {
            observer.invoke(it)
        }
    })
}