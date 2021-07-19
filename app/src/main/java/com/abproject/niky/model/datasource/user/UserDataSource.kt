package com.abproject.niky.model.datasource.user

interface UserDataSource {

    fun signIn(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        postalCode: String,
        address: String,
        age: Int,
    )

    fun loadUserInformation()

    fun signOut()
}