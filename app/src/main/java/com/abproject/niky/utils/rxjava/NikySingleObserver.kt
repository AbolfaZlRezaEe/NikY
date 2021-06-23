package com.abproject.niky.utils.rxjava

import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * this is a custom class for SingleObserver.
 * this class responsible for handling Errors and
 * canceling the requests.
 * take a compositeDisposable and adding disposable in
 * onSubscribe method for canceling request if needed.
 * Under construction...
 */
abstract class NikySingleObserver<T>(
    private val compositeDisposable: CompositeDisposable,
) : SingleObserver<T> {

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        Timber.d(e.toString())
    }
}