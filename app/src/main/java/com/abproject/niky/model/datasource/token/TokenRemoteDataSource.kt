package com.abproject.niky.model.datasource.token

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.dataclass.Message
import com.abproject.niky.model.dataclass.Token
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class TokenRemoteDataSource @Inject constructor(
    private val apiService: NikyApiService,
) : TokenDataSource {

    override fun signIn(jsonObject: JsonObject): Single<Token> {
        return apiService.signIn(jsonObject)
    }

    override fun signUp(jsonObject: JsonObject): Single<Message> {
        return apiService.signUp(jsonObject)
    }

    override fun loadTokenFromSharedPreferences(): Boolean {
        throw IllegalStateException("TokenRemoteDataSource -> this function not available in this class!")
    }

    override fun saveTokenIntoSharedPreferences(
        tokenType: String,
        accessToken: String,
        refreshToken: String,
    ): Boolean {
        throw IllegalStateException("TokenRemoteDataSource -> this function not available in this class!")
    }

    override fun saveTokenIntoTokenContainerObject(
        tokenType: String,
        accessToken: String,
        refreshToken: String,
    ): Boolean {
        throw IllegalStateException("TokenRemoteDataSource -> this function not available in this class!")
    }

    override fun signOut() {
        throw IllegalStateException("TokenRemoteDataSource -> this function not available in this class!")
    }
}