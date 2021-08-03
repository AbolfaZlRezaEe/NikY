package com.abproject.niky.view.views.payment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.abproject.niky.R
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityPaymentResultBinding
import com.abproject.niky.utils.other.UtilFunctions
import com.abproject.niky.utils.other.Variables
import com.abproject.niky.view.views.main.MainActivity
import com.abproject.niky.view.views.orderhistory.OrderHistoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentResultActivity : NikyActivity() {

    private lateinit var binding: ActivityPaymentResultBinding
    private val paymentResultViewModel: PaymentResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeningToTheObservers()

        initializeViews()
    }

    private fun initializeViews() {
        binding.returnHomeMaterialButtonPaymentResult.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.orderHistoryMaterialButtonPaymentResult.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
            finish()
        }
    }

    private fun listeningToTheObservers() {
        paymentResultViewModel.getOrderIdInExtraLiveData.value?.let { response ->
            paymentResultViewModel.getPaymentResult(response)
        }

        paymentResultViewModel.progressbarStatusLiveData.observe(this) { status ->
            showProgressbar(status)
        }

        paymentResultViewModel.paymentResultStatusLiveData.observe(this) { paymentResult ->
            binding.orderPriceTextViewPaymentResult.text =
                UtilFunctions.formatPrice(paymentResult.payablePrice)
            binding.orderStatusTextViewPaymentResult.text = paymentResult.paymentStatus
            binding.purchaseStatusTextViewPaymentResult.text =
                if (paymentResult.purchaseSuccess)
                    getString(R.string.paymentSuccessfully)
                else
                    getString(R.string.paymentFailed)
        }

        paymentResultViewModel.getOrderIdInExtraLiveData.observe(this) { orderId ->
            if (orderId == null) {
                val data: Uri? = intent.data
                data?.let { uri ->
                    val response = uri.getQueryParameter(Variables.JSON_ORDER_ID_KEY)!!.toInt()
                    paymentResultViewModel.getPaymentResult(response)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}