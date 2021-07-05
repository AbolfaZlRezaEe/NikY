package com.abproject.niky.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abproject.niky.R
import com.abproject.niky.model.dataclass.EmptyState
import com.abproject.niky.utils.exceptionhandler.ExceptionType
import com.abproject.niky.utils.exceptionhandler.exceptions.InternetException
import com.abproject.niky.utils.exceptionhandler.NikyExceptionMapper
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus

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
     * _emptyStateStatusLiveData used in all view model that needs but
     * view also can't see and change the value. so i used
     * protected keyword for this reason.
     */
    protected val _emptyStateStatusLiveData = MutableLiveData<EmptyState>()

    /**
     * progressbarStatus is a variable can view reach and use that.
     */
    val progressbarStatus: LiveData<Boolean> get() = _progressbarStatus

    /**
     * emptyStateStatusLiveData is a variable can view reach and use that.
     */
    val emptyStateStatusLiveData: LiveData<EmptyState> get() = _emptyStateStatusLiveData

    /**
     * internetConnectionStatus is a variable that contain internet
     * connection status on all of view models.
     * also this variable can reach in views. because we should be
     * pass value to this variable in the views.
     */
    val internetConnectionStatus = MutableLiveData<Boolean>()

    /**
     * this function check the liveData and internetConnection and after
     * that return true or false.
     * if return true -> it means you can send request to the server.
     * if return false -> it means you can not send request and this
     * method will be show the error!
     */
    fun <T> processForGettingDataInInternetConnection(
        liveData: MutableLiveData<T>? = null,
    ): Boolean {
        return if (internetConnectionStatus.value == true) {
            liveData?.value == null
        } else {
            EventBus.getDefault().post(NikyExceptionMapper.map(
                InternetException(
                    ExceptionType.INTERNET_CONNECTION,
                    R.string.pleaseCheckYourInternetConnection
                )
            ))
            false
        }
    }

    /**
     * this method only check the internet connection and if no problem
     * then return true.
     * else return false and thrown an exception.
     */
    fun checkingInternetConnection(): Boolean {
        return if (internetConnectionStatus.value == true)
            true
        else {
            EventBus.getDefault().post(NikyExceptionMapper.map(
                InternetException(
                    ExceptionType.INTERNET_CONNECTION,
                    R.string.pleaseCheckYourInternetConnection
                )
            ))
            false
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}