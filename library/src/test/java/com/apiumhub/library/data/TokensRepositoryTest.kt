package com.apiumhub.library.data

import arrow.core.Either
import arrow.core.Option
import com.apiumhub.library.UnitTest
import com.apiumhub.library.data.storage.SharedPreferencesService
import com.apiumhub.library.data.storage.TokensStorage
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TokensRepositoryTest : UnitTest() {

    private lateinit var repository: TokensStorage

    private val service = mockk<SharedPreferencesService>(relaxed = true)

    private val tokens = mockFromResource<AuthTokens>(TOKENS_PATH)
    private val tokensJson = getResourceAsString(TOKENS_PATH)

    @BeforeEach
    internal fun setUp() {
        repository = TokensStorage(service, Gson())
    }

    @Test
    internal fun `should retrieve tokens from service`() {
        every {
            service.getString(any())
        } returns Option.just(tokensJson)

        val answer = repository.getTokens()

        answer.isRight() shouldBe true
        answer.get() shouldEqual tokens
    }

    @Test
    internal fun `should return item not found error when item is not stored`() {
        every {
            service.getString(any())
        } returns Option.empty()

        val answer = repository.getTokens()

        answer.isLeft() shouldBe true
        answer.getLeft() shouldBeInstanceOf TokensStorage.ItemNotFound::class.java
    }

    @Test
    internal fun `should return item not found when item is not parsed`() {
        every {
            service.getString(any())
        } returns Option.just("")

        val answer = repository.getTokens()

        answer.isLeft() shouldBe true
        answer.getLeft() shouldBeInstanceOf TokensStorage.ParseException::class.java
    }

    @Test
    internal fun `should save tokens in the service`() {
        repository.saveTokens(tokens)

        verify { service.putString(any(), any()) }
    }

    companion object {
        const val TOKENS_PATH = "mocked_tokens_response.json"
    }
}

fun <A, B> Either<A, B>.getLeft(): A =
        this.fold({
            return it
        }, {
            throw Error()
        })