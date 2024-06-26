package com.mhmtn.satellites2.domain.dataSource

import com.mhmtn.satellites2.data.database.SatelliteDao
import com.mhmtn.satellites2.data.model.SatelliteDetailEntity
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SatelliteLocalDataSource @Inject constructor(
    private val satelliteDao: SatelliteDao
) {
    suspend fun insertSatellite(satelliteDetailEntity: SatelliteDetailEntity) {
        satelliteDao.insertSatellite(satelliteDetailEntity)
    }

    fun getSatellite(satelliteId: Int): Flow<Resource<SatelliteDetailEntity?>> {
        return try {
            satelliteDao.getSatellite(satelliteId).map { Resource.Success(it) }
        } catch (e: Exception) {
            flow { emit(Resource.Error(e.localizedMessage ?: "Error Occurred")) }
        }
    }

    fun checkItemIsExist(satelliteId: Int): Flow<Boolean> {
        return satelliteDao.checkItemIsExist(satelliteId)
    }
}