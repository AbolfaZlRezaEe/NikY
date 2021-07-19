package com.abproject.niky.model.objects

/**
 * this class contain AccessToken and RefreshToken.
 * these variables alive when the application alive.
 */
object TokenContainer {

    var tokenType: String? = null
        private set

    var accessToken: String? = null
        private set
        get() {
            return if (field != null)
                "$tokenType $field"
            else
                null
        }

    var refreshToken: String? = null
        private set

    fun setToken(
        tokenType: String?,
        accessToken: String?,
        refreshToken: String?,
    ) {
        TokenContainer.accessToken = accessToken
        TokenContainer.refreshToken = refreshToken
        TokenContainer.tokenType = tokenType
    }

    fun clearTokenData() {
        tokenType = null
        accessToken = null
        refreshToken = null
    }
}