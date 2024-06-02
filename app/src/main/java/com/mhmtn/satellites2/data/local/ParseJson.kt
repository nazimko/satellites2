package com.mhmtn.satellites2.data.local

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> parseJsonToModel(jsonString: String): T {
    val gson = Gson()
    return gson.fromJson(jsonString, object : TypeToken<T>() {}.type)
}