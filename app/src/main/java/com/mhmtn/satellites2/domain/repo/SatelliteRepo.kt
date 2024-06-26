package com.mhmtn.satellites2.domain.repo

import com.mhmtn.satellites2.data.model.DetailModel
import com.mhmtn.satellites2.data.model.SatelliteDetailEntity
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow

interface SatelliteRepo {
    fun getSatellites(): Flow<Resource<List<SatellitesItem>>>
    suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailEntity?>>
    fun getPositions(id: Int): Flow<Resource<String>>
}