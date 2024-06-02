package com.mhmtn.satellites2.domain.repo

import android.content.Context
import com.mhmtn.satellites2.data.model.SatelliteDetail
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.data.model.Satellites
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow

interface SatelliteRepo {

    suspend fun getSatellites(): Flow<Resource<List<SatellitesItem>>>
    suspend fun getSatelliteDetail(id:Int) : Flow<SatelliteDetailItem>

}