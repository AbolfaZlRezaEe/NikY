package com.abproject.niky.model.datasource.shipping

import com.abproject.niky.model.dataclass.OrderHistoryItem
import com.abproject.niky.model.dataclass.PaymentResult
import com.abproject.niky.model.dataclass.OrderInformation
import com.abproject.niky.model.dataclass.SubmitOrderResult
import io.reactivex.Single

interface ShippingDataSource {

    fun submitOrder(
        orderInformation: OrderInformation,
    ): Single<SubmitOrderResult>

    fun getPaymentResult(
        orderId: Int,
    ): Single<PaymentResult>

    fun getOrderHistory(): Single<List<OrderHistoryItem>>
}