package com.abproject.niky.model.objects

object UserContainer {

    var firstName: String? = null
        private set
    var lastName: String? = null
        private set
    var email: String? = null
        private set
    var phoneNumber: String? = null
        private set
    var postalCode: String? = null
        private set
    var address: String? = null
        private set
    var age: Int = 0
        private set

    fun setUserInformation(
        firstName: String?,
        lastName: String?,
        email: String?,
        phoneNumber: String?,
        postalCode: String?,
        address: String?,
        age: Int,
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.phoneNumber = phoneNumber
        this.postalCode = postalCode
        this.address = address
        this.age = age
    }

    fun clearUserInformation() {
        firstName = null
        lastName = null
        email = null
        phoneNumber = null
        postalCode = null
        address = null
        age = 0
    }
}