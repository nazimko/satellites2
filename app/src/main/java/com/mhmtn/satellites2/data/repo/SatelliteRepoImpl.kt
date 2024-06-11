package com.mhmtn.satellites2.data.repo

import com.mhmtn.satellites2.data.model.PositionsItem
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import javax.inject.Inject
import com.mhmtn.satellites2.domain.dataSource.SatelliteDataSource
import com.mhmtn.satellites2.domain.dataSource.SatelliteLocalDataSource
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class SatelliteRepoImpl @Inject constructor(
        private val dataSource: SatelliteDataSource,
        private val localDataSource: SatelliteLocalDataSource
) : SatelliteRepo {
    override suspend fun getSatellites(): Flow<Resource<List<SatellitesItem>>       > {
        return dataSource.getSatellites()
    }

    override suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailItem?>> = flow {
        if (localDataSource.checkItemIsExist(id).first()) {
            Resource.Success(localDataSource.getSatellite(id))
        } else {
            dataSource.getSatelliteDetail(id)
            localDataSource.insertSatellite(dataSource.getSatelliteDetail(id).first().data!!)
        }
    }

    override suspend fun getPositions(id: Int): Flow<Resource<String>> {
        return dataSource.getPositions(id)
    }
}