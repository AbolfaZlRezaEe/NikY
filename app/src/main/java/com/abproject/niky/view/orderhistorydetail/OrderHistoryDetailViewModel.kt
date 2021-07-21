package com.abproject.niky.view.orderhistorydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.OrderHistoryItem
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_ORDER_HISTORY_ITEM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderHistoryDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : NikyViewModel() {

    private val _getOrderHistoryItem = MutableLiveData<OrderHistoryItem>()

    val getOrderHistoryItem: LiveData<OrderHistoryItem> get() = _getOrderHistoryItem

    init {
        getOrderHistoryItemFromSavedState()
    }

    private fun getOrderHistoryItemFromSavedState() {
        val orderHistoryItem =
            savedStateHandle.get<OrderHistoryItem>(EXTRA_KEY_ORDER_HISTORY_ITEM)!!
        _getOrderHistoryItem.postValue(orderHistoryItem)
    }

}