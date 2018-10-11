package com.apiumhub.library.oauthapium.data.network

import com.apiumhub.library.oauthapium.data.AuthTokens
import retrofit2.Call
import retrofit2.Retrofit

class AuthService(retrofit: Retrofit) {

    private val api = retrofit.create(AuthApi::class.java)

    fun refreshToken(): AuthTokens? = api.refreshToken().execute().body()

}

interface AuthApi {
    fun refreshToken(): Call<AuthTokens>

    class AuthApiImpl(private val api: AuthApi) : AuthApi {
        override fun refreshToken(): Call<AuthTokens> =
                api.refreshToken()

    }
}
