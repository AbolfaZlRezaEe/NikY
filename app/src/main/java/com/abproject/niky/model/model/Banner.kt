package com.abproject.niky.model.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    val id: Int,
    val image: String,
    @SerializedName("link_type")
    val linkType: Int,
    @SerializedName("link_value")
    val linkValue: String,
) : Parcelable