package com.mhmtn.satellites2.util

import android.content.Context

fun Context.readJsonFromAssets(fileName: String): String {
    return this.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
}