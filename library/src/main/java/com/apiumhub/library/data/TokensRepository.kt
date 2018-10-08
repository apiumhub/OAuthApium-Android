package com.apiumhub.library.data

import arrow.core.*
import com.google.gson.Gson

class TokensRepository(private val service: SharedPreferencesService, private val gson: Gson) {

    fun saveTokens(tokens: AuthTokens) =
        service.putString(TOKENS_KEY, gson.toJson(tokens))

    fun getTokens(): Either<Error, AuthTokens> = service
            .getString(TOKENS_KEY).fold(
                    { Left(ItemNotFound()) },
                    { json ->
                        parse(json).fold({
                            Left(ParseException())
                        }, {
                            Right(it)
                        })
                    }
            )

    private fun parse(json: String): Option<AuthTokens> =
            gson.fromJson(json, AuthTokens::class.java).toOption()

    //TODO Move this inside shared preferences service
    companion object {
        const val TOKENS_KEY = "tokens"
    }
}

class ItemNotFound : Error()
class ParseException : Error()