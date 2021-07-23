package com.abproject.niky.model.dataclass

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Token(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("refresh_token")
    val refreshShToken: String,
    @SerializedName("token_type")
    val tokenType: String
)