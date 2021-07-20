package com.abproject.niky.utils.networking

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.dataclass.Token
import com.abproject.niky.model.objects.TokenContainer
import com.abproject.niky.model.repository.user.UserRepository
import com.abproject.niky.utils.exceptionhandler.NikyExceptionMapper
import com.abproject.niky.utils.other.Variables.HEADER_REQUEST_KEY_AUTHORIZATION
import com.abproject.niky.utils.other.Variables.JSON_CLIENT_ID_KEY
import com.abproject.niky.utils.other.Variables.JSON_CLIENT_ID_VALUE
import com.abproject.niky.utils.other.Variables.JSON_CLIENT_SECRET_KEY
import com.abproject.niky.utils.other.Variables.JSON_CLIENT_SECRET_VALUE
import com.abproject.niky.utils.other.Variables.JSON_GRANT_TYPE_KEY
import com.abproject.niky.utils.other.Variables.JSON_REFRESH_TOKEN_KEY
import com.abproject.niky.utils.other.Variables.JSON_REFRESH_TOKEN_VALUE
import com.google.gson.JsonObject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class NikyAuthenticator @Inject constructor(
    private val apiService: NikyApiService,
    private val userRepository: UserRepository,
) : Authenticator {

    override fun authenticate(
        route: Route?,
        response: Response,
    ): Request? {
        if (!TokenContainer.accessToken.isNullOrEmpty()
            && !TokenContainer.refreshToken.isNullOrEmpty()
            && !response.request.url.pathSegments.last().equals("token", false)
        ) {
            try {
                val accessToken = refreshTokenProcess(TokenContainer.refreshToken!!)
                if (accessToken.isEmpty())
                    return null

                return response.request.newBuilder()
                    .header(HEADER_REQUEST_KEY_AUTHORIZATION, accessToken)
                    .build()

            } catch (exceptions: Exception) {
                EventBus.getDefault().post(NikyExceptionMapper.map(exceptions))
            }
        }
        return null
    }

    private fun refreshTokenProcess(
        refreshToken: String,
    ): String {
        val response: retrofit2.Response<Token> =
            apiService.refreshToken(
                JsonObject().apply {
                    addProperty(JSON_GRANT_TYPE_KEY, JSON_REFRESH_TOKEN_VALUE)
                    addProperty(JSON_REFRESH_TOKEN_KEY, refreshToken)
                    addProperty(JSON_CLIENT_ID_KEY, JSON_CLIENT_ID_VALUE)
                    addProperty(JSON_CLIENT_SECRET_KEY, JSON_CLIENT_SECRET_VALUE)
                }
            ).execute()
        response.body()?.let { token ->
            userRepository.onSuccessFullLogin(
                email = null,
                token = token
            )
            return "Bearer ${token.accessToken}"
        }
        return ""
    }


}