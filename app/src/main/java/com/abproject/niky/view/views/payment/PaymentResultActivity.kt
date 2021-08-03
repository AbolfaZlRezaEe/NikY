package com.abproject.niky.view.views.payment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.abproject.niky.R
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.theme.NikyTheme
import com.abproject.niky.utils.other.Variables
import com.abproject.niky.view.views.main.MainActivity
import com.abproject.niky.view.views.orderhistory.OrderHistoryActivity
import com.abproject.niky.view.views.payment.ui.PaymentResultUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentResultActivity : NikyActivity() {

    private val paymentResultViewModel: PaymentResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        processForOrderId()

        setContent {

            val paymentResultStatus by paymentResultViewModel.paymentResultStatusLiveData
                .observeAsState()

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                NikyTheme(darkTheme = false) {
                    paymentResultStatus?.let { paymentResult ->

                        val purchaseStatus = if (paymentResult.purchaseSuccess)
                            getString(R.string.paymentSuccessfully)
                        else
                            getString(R.string.paymentFailed)

                        PaymentResultUi(
                            purchaseStatus = purchaseStatus,
                            orderStatus = paymentResult.paymentStatus,
                            orderPrice = paymentResult.payablePrice,
                            onPressReturnHome = {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            },
                            onPressOrderHistory = {
                                startActivity(Intent(this, OrderHistoryActivity::class.java))
                                finish()
                            }
                        )
                    }
                }
            }
        }
    }

    private fun processForOrderId() {
        val orderId = paymentResultViewModel.getOrderIdInExtras()
        if (orderId == null) {
            val data: Uri? = intent.data
            data?.let { uri ->
                val response = uri.getQueryParameter(Variables.JSON_ORDER_ID_KEY)!!.toInt()
                paymentResultViewModel.getPaymentResult(response)
            }
        } else {
            paymentResultViewModel.getPaymentResult(orderId)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}