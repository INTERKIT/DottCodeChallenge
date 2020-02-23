package com.example.dottchallenge.common.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.disposeOnCleared() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}