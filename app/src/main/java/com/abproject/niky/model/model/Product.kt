package com.abproject.niky.model.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abproject.niky.utils.Variables.PRODUCT_TABLE_NAME

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
)