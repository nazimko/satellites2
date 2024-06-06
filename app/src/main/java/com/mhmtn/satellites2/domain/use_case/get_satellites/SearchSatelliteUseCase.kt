package com.mhmtn.satellites2.domain.use_case.get_satellites

import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.presentation.satellites.SatellitesState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchSatelliteUseCase @Inject constructor()  {
    fun executeSearchSatellite(searchString: String, satellites: List<SatellitesItem>):
            Flow<List<SatellitesItem>> = flow {

            if (searchString.isEmpty()){
                emit(satellites)
            }

            emit(satellites.filter {
                it.name.contains(searchString.trim(),ignoreCase = true)
            })
    }
}
