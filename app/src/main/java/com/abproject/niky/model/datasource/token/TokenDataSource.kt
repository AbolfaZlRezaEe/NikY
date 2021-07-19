package com.abproject.niky.model.datasource.token

import com.abproject.niky.model.dataclass.Message
import com.abproject.niky.model.dataclass.Token
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.Body

interface TokenDataSource {

    fun signIn(
        @Body jsonObject: JsonObject,
    ): Single<Token>

    fun signUp(
        @Body jsonObject: JsonObject,
    ): Single<Message>

    fun loadTokenFromSharedPreferences(): Boolean

    fun saveTokenIntoSharedPreferences(
        tokenType:String,
        accessToken: String,
        refreshToken: String,
    ): Boolean

    fun saveTokenIntoTokenContainerObject(
        tokenType:String,
        accessToken: String,
        refreshToken: String,
    ): Boolean

    fun signOut()
}