package com.abproject.niky.model.dataclass

import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("cart_items")
    val cartItems: List<CartItem>,
    @SerializedName("payable_price")
    val payable_price: Int,
    @SerializedName("shipping_cost")
    val shippingCost: Int,
    @SerializedName("total_price")
    val totalPrice: Int,
)
