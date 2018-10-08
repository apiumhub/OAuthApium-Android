package com.apiumhub.library

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.io.InputStreamReader
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Base class for Unit tests. Inherit from it to create test cases which DO NOT contain android
 * framework dependencies or components.
 *
 * also
 * @see AndroidTest
 * @see IntegrationTest
 */
abstract class UnitTest {

    internal inline fun <reified T> mockFromResource(path: String): T {
        val reader = JsonReader(InputStreamReader(javaClass.classLoader?.getResourceAsStream(path)))
        return Gson().fromJson(reader, T::class.java)
    }

    internal inline fun <reified T> mockListFromResource(path: String): ArrayList<T> {
        val reader = JsonReader(InputStreamReader(javaClass.classLoader?.getResourceAsStream(path)))
        return Gson().fromJson(reader, ParametrizedListType(T::class.java))
    }

    fun getResourceAsString(path: String): String =
        InputStreamReader(javaClass.classLoader?.getResourceAsStream(path)).use { it.readText() }


    /**
     * More info: https://stackoverflow.com/questions/20773850/gson-typetoken-with-dynamic-arraylist-item-type
     */
    internal class ParametrizedListType(private val type: Type) : ParameterizedType {

        override fun getActualTypeArguments(): Array<Type> = arrayOf(type)

        override fun getRawType(): Type = ArrayList::class.java

        override fun getOwnerType(): Type? = null
    }
}
