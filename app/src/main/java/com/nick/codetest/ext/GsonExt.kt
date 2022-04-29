package com.nick.codetest.ext

import com.google.gson.Gson
import java.lang.Exception
import java.lang.reflect.Type

fun <T> Gson.tryParse(s: String, typeOfT: Type): Pair<Boolean, T?> {
    return try {
        val json = this.fromJson<T>(s, typeOfT)
        Pair(true, json)
    } catch (e: Exception) {
        Pair(false, null)
    }
}