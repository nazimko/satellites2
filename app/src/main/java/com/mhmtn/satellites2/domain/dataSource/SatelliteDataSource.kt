package com.mhmtn.satellites2.domain.dataSource

import android.content.Context
import android.util.Log
import com.mhmtn.satellites2.R
import com.mhmtn.satellites2.data.model.PositionsItem
import com.mhmtn.satellites2.util.parseJsonToModel
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.util.Resource
import com.mhmtn.satellites2.util.getItemById
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
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error."))
        }
    }

    suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailItem?>> = flow {
        try {
            val response = context.readJsonFromAssets(context.getString(R.string.SatelliteDetail))
            Log.d("responseDetail",response)
            val item = getItemById(parseJsonToModel<List<SatelliteDetailItem>>(response), id){
                it.id
            }
            if (item != null) {
                Log.d("item",item.id.toString())
            }
            emit(Resource.Success(item))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error."))
        }
    }

    suspend fun getPositions(id: Int): Flow<Resource<PositionsItem?>> = flow {
        try {
            val response = context.readJsonFromAssets(context.getString(R.string.Positions))
            Log.d("responsePosition",response)
            val positions = parseJsonToModel<List<PositionsItem>>(response)
            val position = getItemById(positions, targetId = id){
                it.id.toInt()
            }
            if (position != null) {
                Log.d("position",position.id)
            }
            emit(Resource.Success(position))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error."))
        }
    }
}