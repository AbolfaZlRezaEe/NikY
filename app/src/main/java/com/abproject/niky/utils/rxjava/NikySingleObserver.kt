package com.abproject.niky.utils.rxjava

import com.abproject.niky.utils.exceptionhandler.NikyExceptionMapper
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

/**
 * this is a custom class for SingleObserver.
 * this class responsible for handling Errors and
 * canceling the requests.
 * take a compositeDisposable and adding disposable in
 * onSubscribe method for canceling request if needed.
 */
abstract class NikySingleObserver<T>(
    private val compositeDisposable: CompositeDisposable,
) : SingleObserver<T> {

    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }


    override fun onError(e: Throwable) {
        //handling error that receive from SingleObservers.
        EventBus.getDefault().post(NikyExceptionMapper.map(e))
    }

}