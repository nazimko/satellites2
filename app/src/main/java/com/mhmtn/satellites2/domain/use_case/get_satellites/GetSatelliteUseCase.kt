package com.mhmtn.satellites2.domain.use_case.get_satellites

import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSatelliteUseCase @Inject constructor(private val repo : SatelliteRepo) {
   suspend fun executeGetSatellites () : Flow<Resource<List<SatellitesItem>>>  {
        return repo.getSatellites()
    }
}