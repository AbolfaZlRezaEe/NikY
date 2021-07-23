package com.abproject.niky.model.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderHistoryItem(
    val address: String,
    val date: String,
    @SerializedName("first_name")
    val firstName: String,
    val id: Int,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("order_items")
    val orderItems: List<OrderItem>,
    val payable: Int,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_status")
    val paymentStatus: String,
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("postal_code")
    val postalCode: String,
    @SerializedName("shipping_cost")
    val shippingCost: Int,
    @SerializedName("total")
    val totalPrice: Int,
    @SerializedName("user_id")
    val userId: Int,
) : Parcelable