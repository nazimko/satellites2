package com.mhmtn.satellites2.domain.useCase.getSatelliteDetail

import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSatelliteDetailUseCase @Inject constructor(private val repo:SatelliteRepo) {
    suspend fun executeGetSatelliteDetail (id:Int) : Flow<Resource<SatelliteDetailItem?>> {
        return repo.getSatelliteDetail(id)
    }
}