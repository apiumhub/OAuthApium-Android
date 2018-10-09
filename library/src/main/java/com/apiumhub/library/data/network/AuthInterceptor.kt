package com.apiumhub.library.data.network

import android.util.Log
import com.apiumhub.library.OAuthApium
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

class AuthInterceptor() : Interceptor {
    //TODO Split in two interceptors
    /**
     * Flow:
     *      Get tokens from storage
     *          - If not present -> perform request
     *          - If present -> Inject tokens in request header and perform request
     *      Check response code
     *          - If not 401 -> return response to the outside
     *          - If 401 -> Call refresh
     *                      - If failure -> Do nothing. Returns previous response to the outside
     *                      - If success -> Inject tokens in request header (again) and perform request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = OAuthApium.storage.getTokens().fold({
            Log.d(TAG, "No tokens available. User should login")
            chain.proceed(chain.request())
        }, {
            val original = chain.request()
            val request = original
                    .newBuilder()
                    .header("authorization", "${it.tokenType} ${it.accessToken}")
                    .method(original.method(), original.body())
                    .build()
            chain.proceed(request)
        })

        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            OAuthApium.service.refreshToken()?.let {
                OAuthApium.storage.saveTokens(it)
                val request = response.request()
                        .newBuilder()
                        .header("authorization", "${it.tokenType} ${it.accessToken}")
                        .method(response.request().method(), response.request().body())
                        .build()
                return chain.proceed(request)
            }
        }
        return response
    }

    companion object {
        const val TAG = "AuthInterceptor"
    }
}