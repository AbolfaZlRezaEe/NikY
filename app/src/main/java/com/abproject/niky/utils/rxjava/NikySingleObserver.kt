package com.abproject.niky.utils.rxjava

import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class NikySingleObserver<T>(
    private val compositeDisposable: CompositeDisposable,
) : SingleObserver<T> {

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        Timber.d(e.toString())
        TODO("this method will be developing...")
    }
}