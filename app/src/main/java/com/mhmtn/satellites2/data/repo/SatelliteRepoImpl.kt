package com.mhmtn.satellites2.data.repo

import com.mhmtn.satellites2.data.model.PositionsItem
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import javax.inject.Inject
import com.mhmtn.satellites2.domain.dataSource.SatelliteDataSource
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow

class SatelliteRepoImpl @Inject constructor(
    private val dataSource: SatelliteDataSource
) : SatelliteRepo {
    override suspend fun getSatellites(): Flow<Resource<List<SatellitesItem>>> {
        return dataSource.getSatellites()
    }

    override suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailItem?>> {
        return dataSource.getSatelliteDetail(id)
    }

    override suspend fun getPositions(id: Int): Flow<Resource<String>> {
        return dataSource.getPositions(id)
    }
}