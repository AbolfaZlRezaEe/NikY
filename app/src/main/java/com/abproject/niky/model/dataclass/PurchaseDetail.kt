package com.abproject.niky.model.dataclass

data class PurchaseDetail(
    var totalPrice: Int,
    var payablePrice: Int,
    var shippingCost: Int,
)