package com.mhmtn.satellites2.domain.dataSource

import com.mhmtn.satellites2.data.database.SatelliteDao
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SatelliteLocalDataSource @Inject constructor(
        private val satelliteDao: SatelliteDao
) {
    suspend fun insertSatellite(satelliteDetailItem: SatelliteDetailItem) {
        satelliteDao.insertSatellite(satelliteDetailItem)
    }

    fun getSatellite(satelliteId: Int): Flow<SatelliteDetailItem?> {
        return satelliteDao.getSatellite(satelliteId)
    }

    fun checkItemIsExist(satelliteId: Int): Flow<Boolean> {
        return satelliteDao.checkItemIsExist(satelliteId)
    }
}