package com.abproject.niky.model.dataclass

import com.google.gson.annotations.SerializedName

data class CartItemCount(
    @SerializedName("count")
    var count: Int,
)
