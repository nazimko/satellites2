package com.mhmtn.satellites2.data.repo

import com.mhmtn.satellites2.data.model.DetailModel
import com.mhmtn.satellites2.data.model.SatelliteDetailEntity
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

    override suspend fun getAllDetail(id: Int): Flow<Resource<DetailModel>> = flow {
        try {
            getSatelliteDetail(id = id).collect{
                getPositions(id).collect{position->
                    val z = it.data!!
                  emit(Resource.Success(DetailModel(z.id,z.costPerLaunch,z.height,z.mass,z.firstFlight, positions = position.data!!)))
                }
            }
        }catch (e:Exception){
            emit(Resource.Error(message = "Error."))
        }
    }
}