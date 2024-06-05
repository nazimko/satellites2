package com.mhmtn.satellites2.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> parseJsonToModel(jsonString: String): T {
    val gson = Gson()
    return gson.fromJson(jsonString, object : TypeToken<T>() {}.type)
}

inline fun <reified T> getItemById(dataList: List<T>, targetId: Int, idSelector: (T) -> Int): T? {
    return dataList.find { idSelector(it) == targetId }
}

