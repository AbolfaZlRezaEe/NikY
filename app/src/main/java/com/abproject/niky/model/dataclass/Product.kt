package com.abproject.niky.model.dataclass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abproject.niky.utils.other.Variables.PRODUCT_TABLE_NAME
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = PRODUCT_TABLE_NAME)
data class Product(
    val discount: Int,
    @PrimaryKey
    val id: Int,
    val image: String,
    @SerializedName("previous_price")
    val previousePrice: Long,
    @SerializedName("price")
    val currentPrice: Long,
    val status: Int,
    val title: String,
    //for contain favorite section
    //Note: this variable doesn't receive from server
    var isFavorite: Boolean = false
) : Parcelable