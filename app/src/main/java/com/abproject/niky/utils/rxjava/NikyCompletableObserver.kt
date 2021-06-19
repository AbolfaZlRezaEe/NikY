package com.abproject.niky.utils.rxjava

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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