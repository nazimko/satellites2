package com.mhmtn.satellites2.data.local

import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.util.readJsonFromAssets

class ParseJson {

     fun parseSatelliteJsonToModel(jsonString: String): List<SatellitesItem> {
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<SatellitesItem>>() {}.type)
    }

    fun parseSatelliteDetailJsonToModel(jsonString: String): List<SatelliteDetailItem> {
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<SatelliteDetailItem>>() {}.type)
    }


}