package com.apiumhub.library.oauthapium.data.network

import com.apiumhub.library.oauthapium.data.AuthTokens
import com.apiumhub.library.oauthapium.data.RefreshDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

internal class AuthService(retrofit: Retrofit, private val refreshEndpoint: String) {

    private val api = retrofit.create(AuthApi::class.java)

    fun refreshToken(refreshBody: RefreshDto): AuthTokens? = api.refreshToken(refreshEndpoint, refreshBody).execute().body()
}

internal interface AuthApi {
    @POST
    fun refreshToken(@Url refreshEndpoint: String, @Body refreshBody: RefreshDto): Call<AuthTokens>
}
