package com.abproject.niky.model.dataclass

data class OrderHistoryItem(
    val address: String,
    val date: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val order_items: List<OrderItem>,
    val payable: Int,
    val payment_method: String,
    val payment_status: String,
    val phone: String,
    val postal_code: String,
    val shipping_cost: Int,
    val total: Int,
    val user_id: Int
)