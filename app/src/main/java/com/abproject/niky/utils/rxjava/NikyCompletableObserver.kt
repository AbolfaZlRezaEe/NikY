package com.abproject.niky.utils.rxjava

import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * this is a custom class for CompletableObserver.
 * this class responsible for handling Errors and
 * canceling the requests.
 * take a compositeDisposable and adding disposable in
 * onSubscribe method for canceling request if needed.
 * Under construction...
 */
abstract class NikyCompletableObserver(
    private val compositeDisposable: CompositeDisposable,
) : CompletableObserver {

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        TODO("this method will be developing...")
    }
}