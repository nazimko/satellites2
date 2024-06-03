package com.mhmtn.satellites2.domain.dataSource

import android.content.Context
import com.mhmtn.satellites2.R
import com.mhmtn.satellites2.util.parseJsonToModel
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.util.Resource
import com.mhmtn.satellites2.util.readJsonFromAssets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SatelliteDataSource @Inject constructor(
    private val context : Context
) {

    suspend fun getSatellites(): Flow<Resource<List<SatellitesItem>>> = flow {
        try {
            val response = context.readJsonFromAssets(context.getString(R.string.Satellites))
            emit(Resource.Success(parseJsonToModel<List<SatellitesItem>>(response)))
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "Error."))
        }
    }

    suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailItem>> = flow {
        try {
            val response = context.readJsonFromAssets(context.getString(R.string.SatelliteDetail))
            val item = getItemById(parseJsonToModel<List<SatelliteDetailItem>>(response), id)
            emit(Resource.Success(item!!))
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "Error."))
        }
    }

    private fun getItemById(dataList: List<SatelliteDetailItem>, targetId: Int): SatelliteDetailItem? {
        return dataList.find { it.id == targetId }
    }
}