package com.apiumhub.library.oauthapium

import android.content.Context
import com.apiumhub.library.oauthapium.data.AuthTokens
import com.apiumhub.library.oauthapium.data.network.AuthService
import com.apiumhub.library.oauthapium.data.storage.SharedPreferencesService
import com.apiumhub.library.oauthapium.data.storage.TokensStorage
import com.google.gson.Gson
import retrofit2.Retrofit

class OAuthApiumBuilder {

    private lateinit var context: Context
    private lateinit var refreshEndpoint: String
    private lateinit var retrofitClient: Retrofit

    fun withContext(context: Context): OAuthApiumBuilder = this.apply {
        this.context = context
    }

    fun withRefreshEndpoint(refresh: String) = this.apply {
        this.refreshEndpoint = refresh
    }

    fun withClient(retrofit: Retrofit) = this.apply {
        this.retrofitClient = retrofit
    }

    fun build() {
        //TODO Validate: ::context.isInitialized
        OAuthApium.init(TokensStorage(SharedPreferencesService(context), Gson()), AuthService(retrofitClient, refreshEndpoint))
    }

}

object OAuthApium {

    internal lateinit var storage: TokensStorage
    internal lateinit var service: AuthService

    internal fun init(storage: TokensStorage, service: AuthService) {
        OAuthApium.storage = storage
        OAuthApium.service = service
        //TODO ::storage.isInitialized &&...
    }

    internal fun isInitialized(): Boolean {
        return OAuthApium::storage.isInitialized && OAuthApium::service.isInitialized
    }

    fun saveAuthTokens(tokens: AuthTokens) {
        storage.saveTokens(tokens)
    }

    fun isUserLoggedIn() = storage.getTokens().isRight()

    fun logout() {
        storage.clear()
    }

}