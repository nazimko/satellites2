package com.mhmtn.satellites2.domain.useCase.getSatelliteDetail

import com.mhmtn.satellites2.data.model.DetailModel
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSatelliteDetailUseCase @Inject constructor(private val repo:SatelliteRepo) {
    suspend fun executeGetSatelliteDetail (id:Int) : Flow<Resource<DetailModel>> = flow{
        repo.getSatelliteDetail(id).collect{
            repo.getPositions(id).collect{position->
                val detail = it.data!!
                emit(Resource.Success(DetailModel(detail.id,detail.costPerLaunch,detail.height,detail.mass,detail.firstFlight, positions = position.data!!)))
            }
        }
    }
}