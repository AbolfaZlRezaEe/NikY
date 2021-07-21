package com.abproject.niky.model.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderItem(
    val count: Int,
    val discount: Int,
    val id: Int,
    @SerializedName("order_id")
    val orderId: Int,
    val price: Int,
    val product: Product,
    @SerializedName("product_id")
    val productId: Int,
) : Parcelable