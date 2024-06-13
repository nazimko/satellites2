package com.mhmtn.satellites2.domain.useCase.getSatellites

import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetSatelliteUseCase @Inject constructor(private val repo : SatelliteRepo) {
   suspend fun executeGetSatellites () : Flow<Resource<List<SatellitesItem>>>  {
        return repo.getSatellites()
    }
}