package com.apiumhub.library

import android.content.Context
import com.apiumhub.library.data.network.AuthApi
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test
import retrofit2.Retrofit

class OAuthApiumTest : UnitTest() {

    private val context = mockk<Context>(relaxed = true)
    private val retrofit = mockk<Retrofit>()
    private val api = mockk<AuthApi>()

    @Test
    internal fun `should initialize sdk with parameters`() {
        every {
            retrofit.create(AuthApi::class.java)
        } returns api
        OAuthApiumBuilder().withRefreshEndpoint("/api/refresh").withContext(context).withClient(retrofit).build()
        OAuthApium.isInitialized() `should be` true
    }
}