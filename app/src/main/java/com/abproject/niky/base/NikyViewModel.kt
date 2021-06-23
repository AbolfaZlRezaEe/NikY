package com.abproject.niky.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class NikyViewModel : ViewModel() {

    /**
     * compositeDisposable variable take disposable variables
     * in all requests with rxjava in all view models in application.
     * and if view model is cleared(destroyed) then composite disposable
     * will be dispose and clear.
     */
    var compositeDisposable = CompositeDisposable()

    /**
     * _progressbarStatus used in all view model that needs but
     * view also can't see and change the value. so i used
     * protected keyword for this reason.
     */
    protected val _progressbarStatus = MutableLiveData<Boolean>()

    /**
     * progressbarStatus is a variable can view reach and use that.
     */
    val progressbarStatus: LiveData<Boolean> get() = _progressbarStatus

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}