package com.abproject.niky.model.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseDetail(
    var totalPrice: Long,
    var payablePrice: Long,
    var shippingCost: Long,
) : Parcelable