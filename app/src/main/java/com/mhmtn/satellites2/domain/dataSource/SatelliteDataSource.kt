package com.mhmtn.satellites2.domain.dataSource

import android.content.Context
import androidx.compose.runtime.rememberCoroutineScope
import com.mhmtn.satellites2.data.model.DataList
import com.mhmtn.satellites2.util.parseJsonToModel
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.presentation.satellite_detail.PositionState
import com.mhmtn.satellites2.util.Constants.POSITIONS
import com.mhmtn.satellites2.util.Constants.SATELLITES
import com.mhmtn.satellites2.util.Constants.SATELLITE_DETAIL
import com.mhmtn.satellites2.util.Resource
import com.mhmtn.satellites2.util.getItemById
import com.mhmtn.satellites2.util.readJsonFromAssets
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class SatelliteDataSource @Inject constructor(
        private val context: Context
) {

    suspend fun getSatellites(): Flow<Resource<List<SatellitesItem>>> = flow {
        try {
            val response = context.readJsonFromAssets(SATELLITES)
            emit(Resource.Success(parseJsonToModel<List<SatellitesItem>>(response)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error."))
        }
    }

    suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailItem?>> = flow {
        val result = try {
            val response = context.readJsonFromAssets(SATELLITE_DETAIL)
            val item = getItemById(parseJsonToModel<List<SatelliteDetailItem>>(response), id) {
                it.id
            }
            Resource.Success(item)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error.")
        }
        emit(result)
    }

    suspend fun getPositions(id: Int): Flow<Resource<String>> = flow {
        try {
            val response = context.readJsonFromAssets(POSITIONS)
            val positionItems = parseJsonToModel<DataList>(response)
            val position = positionItems.list.find { it.id == id.toString() }
            val randomPosition = position?.positions!!
            while (true) {
                for (item in randomPosition) {
                    val combinedPosition = "${item.posX} - ${item.posY}"
                    emit(Resource.Success(combinedPosition))
                    delay(3000)
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error."))
        }
    }
}