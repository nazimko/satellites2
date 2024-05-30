package com.mhmtn.satellites2.data.repo

import android.content.Context
import com.mhmtn.satellites2.data.local.ParseJson
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.readJsonFromAssets
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SatelliteRepoImpl @Inject constructor(
    private val api : ParseJson,
    @ApplicationContext private val context : Context) : SatelliteRepo {
    override suspend fun getSatellites(): List<SatellitesItem> {

        val response = readJsonFromAssets(context = context, "satelllites.json")
        return api.parseSatelliteJsonToModel(response)
    }

    override suspend fun getSatelliteDetail(id: Int): SatelliteDetailItem {
        val response = readJsonFromAssets(context,"satellite_detail.json")
        val detail = api.parseSatelliteDetailJsonToModel(response)
        return getItemById(detail,id)!!
    }
}

private fun getItemById(dataList: List<SatelliteDetailItem>, targetId:Int) : SatelliteDetailItem? {
    return dataList.find {
        it.id==targetId
    }
}