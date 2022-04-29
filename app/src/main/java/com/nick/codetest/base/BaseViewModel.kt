package com.nick.codetest.base

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {
    var disposeBag = CompositeDisposable()
        private set

    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }
}