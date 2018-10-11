package com.apiumhub.library

import android.content.Context
import com.apiumhub.library.data.AuthTokens
import com.apiumhub.library.data.network.AuthService
import com.apiumhub.library.data.storage.SharedPreferencesService
import com.apiumhub.library.data.storage.TokensStorage
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
        OAuthApium.init(TokensStorage(SharedPreferencesService(context), Gson()), AuthService(retrofitClient))
    }

}

object OAuthApium {

    lateinit var storage: TokensStorage
    lateinit var service: AuthService

    internal fun init(storage: TokensStorage, service: AuthService) {
        this.storage = storage
        this.service = service
        //TODO ::storage.isInitialized &&...
    }

    internal fun isInitialized(): Boolean {
        return ::storage.isInitialized && ::service.isInitialized
    }

    fun saveAuthTokens(tokens: AuthTokens) {
        this.storage.saveTokens(tokens)
    }

}