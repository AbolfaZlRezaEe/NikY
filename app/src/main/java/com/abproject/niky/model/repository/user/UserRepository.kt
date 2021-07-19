package com.abproject.niky.model.repository.user

import com.abproject.niky.model.dataclass.Message
import com.abproject.niky.model.dataclass.Token
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun signIn(
        username: String,
        password: String,
    ): Completable

    fun signUp(
        username: String,
        password: String,
    ): Completable

    fun signOut()

    fun loadTokenFromSharedPreferences(): Boolean

    fun onSuccessFullLogin(
        token: Token,
    ): Boolean

    fun saveUserInformation(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        postalCode: String,
        address: String,
        age: Int,
    )

    fun loadUserInformation()
}