package com.abproject.niky.model.repository.shipping

import com.abproject.niky.model.dataclass.PaymentResult
import com.abproject.niky.model.dataclass.OrderInformation
import com.abproject.niky.model.dataclass.SubmitOrderResult
import io.reactivex.Single

interface ShippingRepository {

    fun submitOrder(
        orderInformation: OrderInformation,
    ): Single<SubmitOrderResult>

    fun getPaymentResult(
        orderId: Int,
    ): Single<PaymentResult>
}