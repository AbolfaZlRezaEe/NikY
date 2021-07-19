package com.abproject.niky.view.profiledetail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.abproject.niky.R
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityProfileDetailBinding
import com.abproject.niky.model.objects.UserContainer
import com.abproject.niky.utils.other.isValidEmail
import com.abproject.niky.utils.other.validationIranianPhoneNumber
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class ProfileDetailActivity : NikyActivity() {

    private lateinit var binding: ActivityProfileDetailBinding
    private val profileDetailViewModel: ProfileDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeSaveInformation()
        initializeViews()
    }

    private fun initializeViews() {
        profileDetailViewModel.loadUserInformation()
        if (!UserContainer.firstName.isNullOrEmpty()) {
            binding.firstNameTextInputEditText.setText(UserContainer.firstName)
            binding.lastNameTextInputEditText.setText(UserContainer.lastName)
            binding.phoneNumberTextInputEditText.setText(UserContainer.phoneNumber)
            binding.addressTextInputEditText.setText(UserContainer.address)
            binding.emailTextInputEditText.setText(UserContainer.email)
            binding.ageTextInputEditText.setText(UserContainer.age.toString())
            binding.postalCodeTextInputEditText.setText(UserContainer.postalCode)
        }
    }

    private fun initializeSaveInformation() {
        binding.saveInformationMaterialButton.setOnClickListener {
            if (validationEditTexts()) {
                profileDetailViewModel.saveUserInformation(
                    firstName = binding.firstNameTextInputEditText.text.toString(),
                    lastName = binding.lastNameTextInputEditText.text.toString(),
                    phoneNumber = binding.phoneNumberTextInputEditText.text.toString(),
                    email = binding.emailTextInputEditText.text.toString(),
                    postalCode = binding.postalCodeTextInputEditText.text.toString(),
                    address = binding.addressTextInputEditText.text.toString(),
                    age = binding.ageTextInputEditText.text.toString().toInt()
                )
                showSnackBar(getString(R.string.savingUserInformationSuccessfully))
                Timer("finishProfileDetailActivity", false).schedule(3000) {
                    finish()
                }
            } else
                setErrorForTextInputEditTexts()
        }

        binding.toolbarProfileDetail.onBackButtonClickListener = View.OnClickListener {
            finish()
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
                && binding.emailTextInputEditText.text!!.isValidEmail()
                && binding.ageTextInputEditText.text!!.length <= 2
                && binding.ageTextInputEditText.text!!.isNotEmpty()
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
        if (binding.emailTextInputEditText.text.isNullOrEmpty()) {
            binding.emailTextInputEditText.error = getString(R.string.emailError)
        }
        if (!binding.emailTextInputEditText.text!!.isValidEmail()) {
            binding.emailTextInputEditText.error = getString(R.string.wrongEmailError)
        }
        if (binding.ageTextInputEditText.text.isNullOrEmpty()) {
            binding.ageTextInputEditText.error = getString(R.string.ageError)
        }
    }
}