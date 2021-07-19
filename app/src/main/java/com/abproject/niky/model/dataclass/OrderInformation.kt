package com.abproject.niky.model.dataclass


data class OrderInformation(
    val firstName: String,
    val lastName: String,
    val postalCode: String,
    val phoneNumber: String,
    val address: String,
    val paymentMethod: String,
)
