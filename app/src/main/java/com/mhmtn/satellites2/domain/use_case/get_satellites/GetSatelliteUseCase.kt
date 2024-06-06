package com.mhmtn.satellites2.domain.use_case.get_satellites

import android.content.Context
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSatelliteUseCase @Inject constructor(private val repo : SatelliteRepo) {
   suspend fun executeGetSatellites () : Flow<Resource<List<SatellitesItem>>>  {
        return repo.getSatellites()
    }
}