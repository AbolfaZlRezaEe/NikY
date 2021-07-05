package com.abproject.niky.model.dataclass

data class PurchaseDetail(
    var totalPrice: Long,
    var payablePrice: Long,
    var shippingCost: Long,
)