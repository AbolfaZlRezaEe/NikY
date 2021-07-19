package com.abproject.niky.view.profiledetail

import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : NikyViewModel() {

    fun loadUserInformation() {
        userRepository.loadUserInformation()
    }

    fun saveUserInformation(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        postalCode: String,
        address: String,
        age: Int,
    ) {
        userRepository.saveUserInformation(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            postalCode = postalCode,
            address = address,
            age = age
        )
    }
}