package com.abproject.niky.view.views.orderhistorydetail

import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.abproject.niky.R
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.customview.imageview.NikyImageView
import com.abproject.niky.databinding.ActivityOrderHistoryDetailBinding
import com.abproject.niky.model.dataclass.OrderHistoryItem
import com.abproject.niky.model.objects.UserContainer
import com.abproject.niky.utils.other.EnglishConverter
import com.abproject.niky.utils.other.UtilFunctions
import com.abproject.niky.utils.other.UtilFunctions.formatPrice
import com.abproject.niky.utils.other.Variables.PAYMENT_METHOD_ONLINE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryDetailActivity : NikyActivity() {

    private lateinit var binding: ActivityOrderHistoryDetailBinding
    private val orderHistoryDetailViewModel: OrderHistoryDetailViewModel by viewModels()
    private lateinit var layoutParams: LinearLayout.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeningToTheObservers()
    }

    private fun listeningToTheObservers() {
        orderHistoryDetailViewModel.getOrderHistoryItem.observe(this) { orderHistoryItem ->
            setupUi(orderHistoryItem)
        }
    }

    private fun setupUi(
        orderHistoryItem: OrderHistoryItem,
    ) {
        processSizeOfOrderImage()
        binding.toolbarOrderHistoryDetail.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
        binding.orderFirstNameTextViewDetail.text = orderHistoryItem.firstName
        binding.orderLastNameTextViewDetail.text = orderHistoryItem.lastName
        binding.orderPhoneNumberTextViewDetail.text = EnglishConverter
            .convertEnglishNumberToPersianNumber(orderHistoryItem.phoneNumber)
        binding.orderEmailTextViewDetail.text = UserContainer.email
        binding.orderAddressTextViewDetail.text = orderHistoryItem.address
        binding.orderPostalCodeTextViewDetail.text = EnglishConverter
            .convertEnglishNumberToPersianNumber(orderHistoryItem.postalCode)

        if (orderHistoryItem.paymentMethod == PAYMENT_METHOD_ONLINE)
            binding.orderPaymentMethodTextViewDetail.text = getString(R.string.onlinePayment)
        else
            binding.orderPaymentMethodTextViewDetail.text = getString(R.string.cashOnDelivery)

        binding.orderPaymentStatusTextViewDetail.text = getString(R.string.paymentSuccessfully)
        binding.orderShippingTextViewDetail.text = EnglishConverter
            .convertEnglishNumberToPersianNumber(formatPrice(orderHistoryItem.shippingCost).toString())
        binding.orderTotalPriceTextViewDetail.text = EnglishConverter
            .convertEnglishNumberToPersianNumber(formatPrice(orderHistoryItem.totalPrice).toString())

        binding.scroll.postDelayed({
            binding.scroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        },100L)

        orderHistoryItem.orderItems.forEach { orderItem ->
            val imageView = NikyImageView(this)
            imageView.layoutParams = layoutParams
            imageView.setImageURI(orderItem.product.image)
            binding.orderProductsLinearLayoutDetail.addView(imageView)
        }
    }

    private fun processSizeOfOrderImage() {
        val size = UtilFunctions.convertDpToPixel(100f, this).toInt()
        val margin = UtilFunctions.convertDpToPixel(8f, this).toInt()
        layoutParams = LinearLayout.LayoutParams(size, size)
        layoutParams.setMargins(margin, 0, margin, 0)
    }
}