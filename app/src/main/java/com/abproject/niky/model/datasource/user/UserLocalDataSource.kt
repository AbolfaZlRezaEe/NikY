package com.abproject.niky.model.datasource.user

import android.content.SharedPreferences
import com.abproject.niky.model.objects.UserContainer
import com.abproject.niky.utils.other.Variables.SHARED_ADDRESS_KEY
import com.abproject.niky.utils.other.Variables.SHARED_AGE_KEY
import com.abproject.niky.utils.other.Variables.SHARED_EMAIL_KEY
import com.abproject.niky.utils.other.Variables.SHARED_FIRST_NAME_KEY
import com.abproject.niky.utils.other.Variables.SHARED_LAST_NAME_KEY
import com.abproject.niky.utils.other.Variables.SHARED_PHONE_NUMBER_KEY
import com.abproject.niky.utils.other.Variables.SHARED_POSTAL_CODE_KEY
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : UserDataSource {

    override fun signIn(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        postalCode: String,
        address: String,
        age: Int,
    ) {
        sharedPreferences
            .edit()
            .apply {
                putString(SHARED_FIRST_NAME_KEY, firstName)
                putString(SHARED_LAST_NAME_KEY, lastName)
                putString(SHARED_EMAIL_KEY, email)
                putString(SHARED_PHONE_NUMBER_KEY, phoneNumber)
                putString(SHARED_POSTAL_CODE_KEY, postalCode)
                putString(SHARED_ADDRESS_KEY, address)
                putInt(SHARED_AGE_KEY, age)
            }.apply()
        loadUserInformation()
    }

    override fun loadUserInformation() {
        UserContainer.setUserInformation(
            firstName = sharedPreferences.getString(SHARED_FIRST_NAME_KEY, null),
            lastName = sharedPreferences.getString(SHARED_LAST_NAME_KEY, null),
            email = sharedPreferences.getString(SHARED_EMAIL_KEY, null),
            phoneNumber = sharedPreferences.getString(SHARED_PHONE_NUMBER_KEY, null),
            postalCode = sharedPreferences.getString(SHARED_POSTAL_CODE_KEY, null),
            address = sharedPreferences.getString(SHARED_ADDRESS_KEY, null),
            age = sharedPreferences.getInt(SHARED_AGE_KEY, 0)
        )
    }

    override fun signOut() {
        sharedPreferences
            .edit()
            .clear()
            .apply()

        UserContainer.clearUserInformation()
    }
}