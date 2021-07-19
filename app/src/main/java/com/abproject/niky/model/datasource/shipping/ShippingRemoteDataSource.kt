package com.abproject.niky.model.datasource.shipping

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.dataclass.PaymentResult
import com.abproject.niky.model.dataclass.OrderInformation
import com.abproject.niky.model.dataclass.SubmitOrderResult
import com.abproject.niky.utils.other.Variables.JSON_ADDRESS_KEY
import com.abproject.niky.utils.other.Variables.JSON_FIRST_NAME_KEY
import com.abproject.niky.utils.other.Variables.JSON_LAST_NAME_KEY
import com.abproject.niky.utils.other.Variables.JSON_PAYMENT_METHOD_KEY
import com.abproject.niky.utils.other.Variables.JSON_PHONE_NUMBER_KEY
import com.abproject.niky.utils.other.Variables.JSON_POSTAL_CODE_KEY
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class ShippingRemoteDataSource @Inject constructor(
    private val apiService: NikyApiService,
) : ShippingDataSource {

    override fun submitOrder(
        orderInformation: OrderInformation,
    ): Single<SubmitOrderResult> {
        return apiService.submitOrder(JsonObject().apply {
            addProperty(JSON_FIRST_NAME_KEY, orderInformation.firstName)
            addProperty(JSON_LAST_NAME_KEY, orderInformation.lastName)
            addProperty(JSON_POSTAL_CODE_KEY, orderInformation.postalCode)
            addProperty(JSON_PHONE_NUMBER_KEY, orderInformation.phoneNumber)
            addProperty(JSON_ADDRESS_KEY, orderInformation.address)
            addProperty(JSON_PAYMENT_METHOD_KEY, orderInformation.paymentMethod)
        })
    }

    override fun getPaymentResult(
        orderId: Int,
    ): Single<PaymentResult> {
        return apiService.getPaymentResult(orderId)
    }
}