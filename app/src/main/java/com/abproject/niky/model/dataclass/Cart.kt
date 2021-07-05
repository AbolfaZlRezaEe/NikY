package com.abproject.niky.model.dataclass

import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("cart_items")
    val cartItems: List<CartItem>,
    @SerializedName("payable_price")
    val payable_price: Long,
    @SerializedName("shipping_cost")
    val shippingCost: Long,
    @SerializedName("total_price")
    val totalPrice: Long,
)
