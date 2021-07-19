package com.abproject.niky.model.dataclass

import com.google.gson.annotations.SerializedName

data class PaymentResult(
    @SerializedName("payable_price")
    val payablePrice: Int,
    @SerializedName("payment_status")
    val paymentStatus: String,
    @SerializedName("purchase_success")
    val purchaseSuccess: Boolean
)