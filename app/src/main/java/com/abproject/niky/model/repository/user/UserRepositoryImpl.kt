package com.abproject.niky.model.repository.user

import com.abproject.niky.model.dataclass.Token
import com.abproject.niky.model.datasource.token.TokenDataSource
import com.abproject.niky.model.datasource.user.UserDataSource
import com.abproject.niky.utils.other.Variables.JSON_CLIENT_ID_KEY
import com.abproject.niky.utils.other.Variables.JSON_CLIENT_ID_VALUE
import com.abproject.niky.utils.other.Variables.JSON_CLIENT_SECRET_KEY
import com.abproject.niky.utils.other.Variables.JSON_CLIENT_SECRET_VALUE
import com.abproject.niky.utils.other.Variables.JSON_EMAIL_KEY
import com.abproject.niky.utils.other.Variables.JSON_GRANT_TYPE_KEY
import com.abproject.niky.utils.other.Variables.JSON_GRANT_TYPE_VALUE
import com.abproject.niky.utils.other.Variables.JSON_PASSWORD_KEY
import com.abproject.niky.utils.other.Variables.JSON_USERNAME_KEY
import com.google.gson.JsonObject
import io.reactivex.Completable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val tokenRemoteDataSource: TokenDataSource,
    private val tokenLocalDataSource: TokenDataSource,
    private val userLocalDataSource: UserDataSource,
) : UserRepository {

    override fun signIn(
        username: String,
        password: String,
    ): Completable {
        return tokenRemoteDataSource.signIn(
            JsonObject().apply {
                addProperty(JSON_USERNAME_KEY, username)
                addProperty(JSON_PASSWORD_KEY, password)
                addProperty(JSON_GRANT_TYPE_KEY, JSON_GRANT_TYPE_VALUE)
                addProperty(JSON_CLIENT_ID_KEY, JSON_CLIENT_ID_VALUE)
                addProperty(JSON_CLIENT_SECRET_KEY, JSON_CLIENT_SECRET_VALUE)
            }
        ).doOnSuccess { token ->
            //save token in shared preferences and TokenContainer
            onSuccessFullLogin(
                email = username,
                token = token
            )
        }.ignoreElement()
    }

    override fun signUp(
        username: String,
        password: String,
    ): Completable {
        return tokenRemoteDataSource.signUp(
            JsonObject().apply {
                addProperty(JSON_EMAIL_KEY, username)
                addProperty(JSON_PASSWORD_KEY, password)
            }
        ).flatMap {
            //after signup into application, we should be signin into application
            tokenRemoteDataSource.signIn(
                JsonObject().apply {
                    addProperty(JSON_USERNAME_KEY, username)
                    addProperty(JSON_PASSWORD_KEY, password)
                    addProperty(JSON_GRANT_TYPE_KEY, JSON_GRANT_TYPE_VALUE)
                    addProperty(JSON_CLIENT_ID_KEY, JSON_CLIENT_ID_VALUE)
                    addProperty(JSON_CLIENT_SECRET_KEY, JSON_CLIENT_SECRET_VALUE)
                }
            )
        }.doOnSuccess { token ->
            onSuccessFullLogin(
                email = username,
                token = token
            )
        }.ignoreElement()
    }

    override fun signOut() {
        tokenLocalDataSource.signOut()
        userLocalDataSource.signOut()
    }

    override fun loadTokenFromSharedPreferences(): Boolean {
        return tokenLocalDataSource.loadTokenFromSharedPreferences()
    }

    override fun onSuccessFullLogin(
        email: String?,
        token: Token,
    ): Boolean {
        userLocalDataSource.signIn(
            firstName = "",
            lastName = "",
            email = email ?: "",
            phoneNumber = "",
            postalCode = "",
            address = "",
            age = 0
        )
        tokenLocalDataSource.saveTokenIntoTokenContainerObject(
            tokenType = token.tokenType,
            accessToken = token.accessToken,
            refreshToken = token.refreshShToken
        )
        tokenLocalDataSource.saveTokenIntoSharedPreferences(
            tokenType = token.tokenType,
            accessToken = token.accessToken,
            refreshToken = token.refreshShToken
        )
        return true
    }

    override fun saveUserInformation(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        postalCode: String,
        address: String,
        age: Int,
    ) {
        userLocalDataSource.signIn(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            postalCode = postalCode,
            address = address,
            age = age
        )
    }

    override fun loadUserInformation() {
        userLocalDataSource.loadUserInformation()
    }
}