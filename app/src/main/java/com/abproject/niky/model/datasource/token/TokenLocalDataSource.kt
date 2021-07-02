package com.abproject.niky.model.datasource.token

import android.content.SharedPreferences
import com.abproject.niky.model.dataclass.Message
import com.abproject.niky.model.dataclass.Token
import com.abproject.niky.model.dataclass.TokenContainer
import com.abproject.niky.utils.other.Variables.SHARED_ACCESS_TOKE_KEY
import com.abproject.niky.utils.other.Variables.SHARED_REFRESH_TOKEN_KEY
import com.abproject.niky.utils.other.Variables.SHARED_TOKEN_TYPE_KEY
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class TokenLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : TokenDataSource {

    override fun signIn(
        jsonObject: JsonObject,
    ): Single<Token> {
        TODO("Not yet implemented")
    }

    override fun signUp(
        jsonObject: JsonObject,
    ): Single<Message> {
        TODO("Not yet implemented")
    }

    override fun loadTokenFromSharedPreferences(): Boolean {
        TokenContainer.setToken(
            sharedPreferences.getString(SHARED_TOKEN_TYPE_KEY, null),
            sharedPreferences.getString(SHARED_ACCESS_TOKE_KEY, null),
            sharedPreferences.getString(SHARED_REFRESH_TOKEN_KEY, null)
        )
        return true
    }

    override fun saveTokenIntoSharedPreferences(
        tokenType: String,
        accessToken: String,
        refreshToken: String,
    ): Boolean {
        sharedPreferences.edit().apply {
            putString(SHARED_TOKEN_TYPE_KEY, tokenType)
            putString(SHARED_ACCESS_TOKE_KEY, accessToken)
            putString(SHARED_REFRESH_TOKEN_KEY, refreshToken)
        }.apply()
        return true
    }

    override fun saveTokenIntoTokenContainerObject(
        tokenType: String,
        accessToken: String,
        refreshToken: String,
    ): Boolean {
        TokenContainer.setToken(
            tokenType = tokenType,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
        return true
    }
}