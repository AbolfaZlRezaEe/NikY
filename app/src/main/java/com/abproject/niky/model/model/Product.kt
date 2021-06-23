package com.abproject.niky.model.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abproject.niky.utils.Variables.PRODUCT_TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = PRODUCT_TABLE_NAME)
data class Product(
    val discount: Int,
    @PrimaryKey
    val id: Int,
    val image: String,
    val previous_price: Int,
    val price: Int,
    val status: Int,
    val title: String,
) : Parcelable