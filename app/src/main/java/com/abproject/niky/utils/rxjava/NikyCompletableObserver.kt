package com.abproject.niky.utils.rxjava

import com.abproject.niky.utils.exceptionhandler.NikyExceptionMapper
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

/**
 * this is a custom class for CompletableObserver.
 * this class responsible for handling Errors and
 * canceling the requests.
 * take a compositeDisposable and adding disposable in
 * onSubscribe method for canceling request if needed.
 */
abstract class NikyCompletableObserver(
    private val compositeDisposable: CompositeDisposable,
) : CompletableObserver {

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        //handling error that receive from CompletableObservers.
        EventBus.getDefault().post(NikyExceptionMapper.map(e))
        Timber.d("NikyCompletableObserver -> $e")
    }
}