package com.abproject.niky.model.dataclass

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
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        this.tokenType = tokenType
    }

    fun clearTokenData() {
        this.tokenType = null
        this.accessToken = null
        this.refreshToken = null
    }
}