package com.abproject.niky.model.repository.shipping

import com.abproject.niky.model.dataclass.PaymentResult
import com.abproject.niky.model.dataclass.OrderInformation
import com.abproject.niky.model.dataclass.SubmitOrderResult
import com.abproject.niky.model.datasource.shipping.ShippingDataSource
import io.reactivex.Single
import javax.inject.Inject

class ShippingRepositoryImpl @Inject constructor(
    private val shippingRemoteDataSource: ShippingDataSource,
) : ShippingRepository {

    override fun submitOrder(
        orderInformation: OrderInformation,
    ): Single<SubmitOrderResult> {
        return shippingRemoteDataSource.submitOrder(orderInformation)
    }

    override fun getPaymentResult(
        orderId: Int,
    ): Single<PaymentResult> {
        return shippingRemoteDataSource.getPaymentResult(orderId)
    }
}