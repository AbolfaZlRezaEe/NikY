package com.abproject.niky.view.views.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.PaymentResult
import com.abproject.niky.model.repository.shipping.ShippingRepository
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_ORDER_ID
import com.abproject.niky.utils.other.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentResultViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val shippingRepository: ShippingRepository,
) : NikyViewModel() {

    private val _paymentResultStatusLiveData = MutableLiveData<PaymentResult>()
    val paymentResultStatusLiveData: LiveData<PaymentResult> get() = _paymentResultStatusLiveData

    fun getOrderIdInExtras(): Int? {
        return savedStateHandle.get<Int>(EXTRA_KEY_ORDER_ID)
    }

    fun getPaymentResult(
        orderId: Int
    ) {
        _progressbarStatusLiveData.value = true
        shippingRepository.getPaymentResult(orderId)
            .asyncNetworkRequest()
            .doFinally { _progressbarStatusLiveData.postValue(false) }
            .subscribe(object : NikySingleObserver<PaymentResult>(compositeDisposable) {
                override fun onSuccess(response: PaymentResult) {
                    _paymentResultStatusLiveData.value = response
                }
            })
    }
}