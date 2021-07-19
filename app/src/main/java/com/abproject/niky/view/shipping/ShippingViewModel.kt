package com.abproject.niky.view.shipping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.PurchaseDetail
import com.abproject.niky.model.dataclass.OrderInformation
import com.abproject.niky.model.dataclass.SubmitOrderResult
import com.abproject.niky.model.repository.shipping.ShippingRepository
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PURCHASE_DETAIL
import com.abproject.niky.utils.other.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShippingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val shippingRepository: ShippingRepository,
) : NikyViewModel() {

    private val _submitOrderStatusLiveData = MutableLiveData<SubmitOrderResult>()
    private val _purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()

    val submitOrderStatusLiveData: LiveData<SubmitOrderResult> get() = _submitOrderStatusLiveData
    val purchaseDetailLiveData: LiveData<PurchaseDetail> get() = _purchaseDetailLiveData

    init {
        _purchaseDetailLiveData.value = getPurchaseDetail(savedStateHandle)
    }

    private fun getPurchaseDetail(
        savedStateHandle: SavedStateHandle,
    ): PurchaseDetail {
        return savedStateHandle.get<PurchaseDetail>(EXTRA_KEY_PURCHASE_DETAIL)!!
    }

    fun submitOrder(
        orderInformation: OrderInformation,
    ) {
        _progressbarStatusLiveData.value = true
        shippingRepository.submitOrder(orderInformation)
            .asyncNetworkRequest()
            .doFinally { _progressbarStatusLiveData.postValue(false) }
            .subscribe(object : NikySingleObserver<SubmitOrderResult>(compositeDisposable) {
                override fun onSuccess(response: SubmitOrderResult) {
                    _submitOrderStatusLiveData.value = response
                }
            })

    }


}