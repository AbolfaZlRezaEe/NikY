package com.abproject.niky.model.model

import com.google.gson.annotations.SerializedName

data class AddToCart(
    @SerializedName("count")
    val count: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
)
