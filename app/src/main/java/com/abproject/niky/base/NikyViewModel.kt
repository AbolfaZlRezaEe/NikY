package com.abproject.niky.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abproject.niky.model.dataclass.EmptyState
import io.reactivex.disposables.CompositeDisposable

abstract class NikyViewModel : ViewModel() {


    /**
     * compositeDisposable variable take disposable variables
     * in all requests with rxjava in all view models in application.
     * and if view model is cleared(destroyed) then composite disposable
     * will be dispose and clear.
     */
    val compositeDisposable = CompositeDisposable()

    /**
     * _progressbarStatus used in all view model that needs but
     * view also can't see and change the value. so i used
     * protected keyword for this reason.
     */
    protected val _progressbarStatusLiveData = MutableLiveData<Boolean>()

    /**
     * _emptyStateStatusLiveData used in all view model that needs but
     * view also can't see and change the value. so i used
     * protected keyword for this reason.
     */
    protected val _emptyStateStatusLiveData = MutableLiveData<EmptyState>()

    /**
     * progressbarStatus is a variable can view reach and use that.
     */
    val progressbarStatusLiveData: LiveData<Boolean> get() = _progressbarStatusLiveData

    /**
     * emptyStateStatusLiveData is a variable can view reach and use that.
     */
    val emptyStateStatusLiveData: LiveData<EmptyState> get() = _emptyStateStatusLiveData


    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}