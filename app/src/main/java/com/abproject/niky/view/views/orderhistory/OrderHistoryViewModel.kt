package com.abproject.niky.view.views.orderhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abproject.niky.R
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.EmptyState
import com.abproject.niky.model.dataclass.OrderHistoryItem
import com.abproject.niky.model.repository.shipping.ShippingRepository
import com.abproject.niky.utils.other.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val shippingRepository: ShippingRepository,
) : NikyViewModel() {

    private val _getOrderHistoryItemsLiveData = MutableLiveData<List<OrderHistoryItem>>()

    val getOrderHistoryItemsLiveData: LiveData<List<OrderHistoryItem>> get() = _getOrderHistoryItemsLiveData

    init {
        getOrderHistory()
    }

    private fun getOrderHistory() {
        _progressbarStatusLiveData.value = true
        shippingRepository.getOrderHistory()
            .asyncNetworkRequest()
            .doFinally { _progressbarStatusLiveData.postValue(false) }
            .subscribe(object : NikySingleObserver<List<OrderHistoryItem>>(compositeDisposable) {
                override fun onSuccess(response: List<OrderHistoryItem>) {
                    if (response.isEmpty()) {
                        _emptyStateStatusLiveData.value = EmptyState(
                            true,
                            R.string.nothingForShowInOrders
                        )
                    } else {
                        _emptyStateStatusLiveData.value = EmptyState(false)
                        _getOrderHistoryItemsLiveData.value = response
                    }
                }
            })
    }
}