package com.mhmtn.satellites2.domain.useCase.getSatelliteDetail

import com.mhmtn.satellites2.data.model.DetailModel
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSatelliteDetailUseCase @Inject constructor(private val repo: SatelliteRepo) {

    suspend fun executeGetSatelliteDetail(id: Int): Flow<Resource<DetailModel>> {
        val detailFlow = repo.getSatelliteDetail(id)
        val positionsFlow = repo.getPositions(id)
        return combine(detailFlow, positionsFlow) { detailResource, positionResource ->
            if (detailResource is Resource.Success && positionResource is Resource.Success) {
                val detail = detailResource.data!!
                val positions = positionResource.data!!
                Resource.Success(
                    DetailModel(
                        detail.id,
                        detail.costPerLaunch,
                        detail.height,
                        detail.mass,
                        detail.firstFlight,
                        positions = positions
                    )
                )
            } else {
                Resource.Error("Error")
            }
        }
    }
}