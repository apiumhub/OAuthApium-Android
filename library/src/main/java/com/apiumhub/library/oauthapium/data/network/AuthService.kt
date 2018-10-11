package com.apiumhub.library.oauthapium.data.network

import com.apiumhub.library.oauthapium.data.AuthTokens
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.POST
import retrofit2.http.Url

internal class AuthService(retrofit: Retrofit, private val refreshEndpoint: String) {

    private val api = retrofit.create(AuthApi::class.java)

    fun refreshToken(): AuthTokens? = api.refreshToken(refreshEndpoint).execute().body()
}

internal interface AuthApi {
    @POST
    fun refreshToken(@Url refreshEndpoint: String): Call<AuthTokens>
}
