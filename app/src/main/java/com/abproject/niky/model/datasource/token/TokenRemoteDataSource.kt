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
        TODO("Not yet implemented")
    }

    override fun saveTokenIntoSharedPreferences(
        tokenType: String,
        accessToken: String,
        refreshToken: String,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun saveTokenIntoTokenContainerObject(
        tokenType: String,
        accessToken: String,
        refreshToken: String,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}