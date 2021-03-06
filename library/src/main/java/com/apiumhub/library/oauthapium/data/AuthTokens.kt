package com.apiumhub.library.oauthapium.data

import com.apiumhub.library.oauthapium.core.extension.empty
import com.google.gson.annotations.SerializedName

data class AuthTokens(
        @SerializedName("access_token")
        val accessToken: String = String.empty(),
        @SerializedName("expires_in")
        val expiresIn: Int = 0,
        @SerializedName("token_type")
        val tokenType: String = String.empty(),
        @SerializedName("refresh_token")
        val refreshToken: String = String.empty()
)

data class RefreshDto(
        @SerializedName("refresh_token")
        val refreshToken: String?
)