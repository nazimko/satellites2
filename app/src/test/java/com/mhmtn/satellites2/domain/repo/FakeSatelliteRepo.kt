package com.mhmtn.satellites2.domain.repo

import com.mhmtn.satellites2.data.model.SatelliteDetailEntity
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSatelliteRepo : SatelliteRepo {

    private val satellite1 = SatellitesItem(false, 1, "Satellite 1")
    private val satellite2 = SatellitesItem(false, 2, "Satellite 2")
    private val satellite3 = SatellitesItem(true, 3, "Satellite 3")

    private val fakeSatelliteList = listOf(satellite1, satellite2, satellite3)

    private val fakeSatelliteDetailEntity = SatelliteDetailEntity(
        costPerLaunch = 1000,
        firstFlight = "13.03.1990",
        height = 500,
        id = 1,
        mass = 40000
    )

    private val fakePositions = "Latitude/Longitude"

    override fun getSatellites(): Flow<Resource<List<SatellitesItem>>> {
        return flow { emit(Resource.Success(fakeSatelliteList)) }
    }

    override suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailEntity?>> {
        return flow { emit(Resource.Success(fakeSatelliteDetailEntity)) }
    }

    override fun getPositions(id: Int): Flow<Resource<String>> {
        return flow { emit(Resource.Success(fakePositions)) }
    }
}