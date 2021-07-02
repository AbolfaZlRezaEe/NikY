package com.abproject.niky.model.dataclass

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String
)