package com.abproject.niky.model.dataclass

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("cart_item_id")
    val cartItemId: Int,
    @SerializedName("count")
    var count: Int,
    @SerializedName("product")
    val product: Product,
    var changeCountProgressBarIsVisible: Boolean = false
)