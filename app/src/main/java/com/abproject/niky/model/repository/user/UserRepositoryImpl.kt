package com.abproject.niky.model.repository.user

import com.abproject.niky.model.dataclass.Token
import com.abproject.niky.model.datasource.token.TokenDataSource
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
            onSuccessFullLogin(token)
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
            onSuccessFullLogin(token)
        }.ignoreElement()
    }


    override fun loadTokenFromSharedPreferences(): Boolean {
        return tokenLocalDataSource.loadTokenFromSharedPreferences()
    }

    override fun onSuccessFullLogin(
        token: Token,
    ): Boolean {
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
}