package com.apiumhub.library.data.storage

import android.content.Context
import arrow.core.Option
import arrow.core.toOption

class SharedPreferencesService(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getString(key: String): Option<String> =
            sharedPreferences.getString(key, null).toOption()

    fun putString(key: String, value: String) {
        sharedPreferences.edit().apply {
            putString(key, value)
            apply()
        }
    }

    companion object {
        const val SHARED_PREFERENCES_NAME = "OAuthApiumSP"
    }
}