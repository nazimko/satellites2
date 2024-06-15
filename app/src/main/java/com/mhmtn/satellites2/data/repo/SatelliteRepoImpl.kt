package com.mhmtn.satellites2.data.repo

import com.mhmtn.satellites2.data.model.SatelliteDetailEntity
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import javax.inject.Inject
import com.mhmtn.satellites2.domain.dataSource.SatelliteDataSource
import com.mhmtn.satellites2.domain.dataSource.SatelliteLocalDataSource
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SatelliteRepoImpl @Inject constructor(
        private val dataSource: SatelliteDataSource,
        private val localDataSource: SatelliteLocalDataSource
) : SatelliteRepo {
    override suspend fun getSatellites(): Flow<Resource<List<SatellitesItem>>> {
        return dataSource.getSatellites()
    }
    override suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailEntity?>> {
        return if (localDataSource.checkItemIsExist(id).first()) {
            localDataSource.getSatellite(id)
        } else {
            dataSource.getSatelliteDetail(id).also {
                localDataSource.insertSatellite(it.first().data!!)
            }
        }
    }
    override suspend fun getPositions(id: Int): Flow<Resource<String>> {
        return dataSource.getPositions(id)
    }
}