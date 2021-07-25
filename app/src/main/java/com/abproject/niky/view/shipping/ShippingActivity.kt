package com.abproject.niky.view.shipping

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.abproject.niky.R
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityShippingBinding
import com.abproject.niky.model.dataclass.OrderInformation
import com.abproject.niky.model.objects.UserContainer
import com.abproject.niky.utils.other.UtilFunctions
import com.abproject.niky.utils.other.Variables
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_ORDER_ID
import com.abproject.niky.utils.other.validationIranianPhoneNumber
import com.abproject.niky.view.cart.CartItemAdapter
import com.abproject.niky.view.main.MainActivity
import com.abproject.niky.view.payment.PaymentResultActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShippingActivity : NikyActivity() {

    private lateinit var binding: ActivityShippingBinding
    private val shippingViewModel: ShippingViewModel by viewModels()
    private lateinit var purchaseViewHolder: CartItemAdapter.PurchaseItemViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeningToTheObservers()

        initializeViews()

        initializeUserDetailInformation()
    }

    private fun initializeUserDetailInformation() {
        if (!UserContainer.firstName.isNullOrEmpty()) {
            binding.firstNameTextInputEditText.setText(UserContainer.firstName)
            binding.lastNameTextInputEditText.setText(UserContainer.lastName)
            binding.phoneNumberTextInputEditText.setText(UserContainer.phoneNumber)
            binding.postalCodeTextInputEditText.setText(UserContainer.postalCode)
            binding.addressTextInputEditText.setText(UserContainer.address)
        }
    }

    private fun initializeViews() {
        val purchaseDetailView = findViewById<View>(R.id.purchaseDetailShippingInformation)!!
        purchaseViewHolder = CartItemAdapter.PurchaseItemViewHolder(purchaseDetailView)

        binding.toolbarShipping.onBackButtonClickListener = View.OnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val paymentOnClickListener = View.OnClickListener { view ->
            if (validationEditTexts()) {
                val orderInformation = OrderInformation(
                    firstName = binding.firstNameTextInputEditText.text!!.toString(),
                    lastName = binding.lastNameTextInputEditText.text!!.toString(),
                    phoneNumber = binding.phoneNumberTextInputEditText.text!!.toString(),
                    postalCode = binding.postalCodeTextInputEditText.text!!.toString(),
                    address = binding.addressTextInputEditText.text!!.toString(),
                    paymentMethod =
                    if (view.id == R.id.onlinePaymentMaterialButton)
                        Variables.PAYMENT_METHOD_ONLINE
                    else
                        Variables.PAYMENT_METHOD_CASH_ON_DELIVERY
                )
                shippingViewModel.submitOrder(orderInformation)
            } else
                setErrorForTextInputEditTexts()
        }

        binding.onlinePaymentMaterialButton.setOnClickListener(paymentOnClickListener)
        binding.cashOnDeliveryMaterialButton.setOnClickListener(paymentOnClickListener)

        /* TODO: 7/19/2021 create functionality that convert english number in phone number
                 and postal card to persian number
        */
    }


    private fun listeningToTheObservers() {
        shippingViewModel.purchaseDetailLiveData.observe(this) { purchaseDetail ->
            purchaseViewHolder.bindPurchaseItem(purchaseDetail)
        }
        shippingViewModel.progressbarStatusLiveData.observe(this) { status ->
            showProgressbar(status)
        }
        shippingViewModel.submitOrderStatusLiveData.observe(this) { submitOrderResult ->
            if (submitOrderResult.bankGatewayUrl.isNotEmpty()) {
                UtilFunctions.openUrlInCustomTab(this, submitOrderResult.bankGatewayUrl)
            } else {
                startActivity(Intent(this, PaymentResultActivity::class.java).apply {
                    putExtra(EXTRA_KEY_ORDER_ID, submitOrderResult.orderId)
                })
            }
            binding.onlinePaymentMaterialButton.isEnabled = false
            binding.cashOnDeliveryMaterialButton.isEnabled = false
            this.finish()
        }
    }

    private fun validationEditTexts(): Boolean {
        return binding.firstNameTextInputEditText.text!!.isNotEmpty()
                && binding.lastNameTextInputEditText.text!!.isNotEmpty()
                && validationIranianPhoneNumber(binding.phoneNumberTextInputEditText.text.toString())
                && binding.phoneNumberTextInputEditText.text!!.isNotEmpty()
                && binding.postalCodeTextInputEditText.text!!.isNotEmpty()
                && binding.postalCodeTextInputEditText.text!!.length == 10
                && binding.addressTextInputEditText.text!!.isNotEmpty()
                && binding.addressTextInputEditText.text!!.length >= 20
                && binding.addressTextInputEditText.text!!.length <= 50
    }

    private fun setErrorForTextInputEditTexts() {
        if (binding.firstNameTextInputEditText.text.isNullOrEmpty()) {
            binding.firstNameTextInputEditText.error = getString(R.string.firstNameError)
        }
        if (binding.lastNameTextInputEditText.text.isNullOrEmpty()) {
            binding.lastNameTextInputEditText.error = getString(R.string.lastNameError)
        }
        if (binding.phoneNumberTextInputEditText.text.isNullOrEmpty()) {
            binding.phoneNumberTextInputEditText.error = getString(R.string.phoneNumberError)
        }
        if (!validationIranianPhoneNumber(binding.phoneNumberTextInputEditText.text.toString())) {
            binding.phoneNumberTextInputEditText.error = getString(R.string.phoneNumberSchemeError)
        }
        if (binding.postalCodeTextInputEditText.text.isNullOrEmpty()) {
            binding.postalCodeTextInputEditText.error = getString(R.string.postalCardError)
        }
        if (binding.postalCodeTextInputEditText.text!!.length != 10) {
            binding.postalCodeTextInputEditText.error = getString(R.string.postalCardSchemeError)
        }
        if (binding.addressTextInputEditText.text.isNullOrEmpty()) {
            binding.addressTextInputEditText.error = getString(R.string.addressError)
        }
        if (binding.addressTextInputEditText.text!!.length != 20) {
            binding.addressTextInputEditText.error = getString(R.string.addressSchemeError)
        }
        if (binding.addressTextInputEditText.text!!.length > 50) {
            binding.addressTextInputEditText.error = getString(R.string.addressSchemeError2)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}