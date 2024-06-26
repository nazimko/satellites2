package com.mhmtn.satellites2.domain.useCase.getSatellites

import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchSatelliteUseCase @Inject constructor(
    private val getSatelliteUseCase: GetSatelliteUseCase

) {
    operator fun invoke(searchString: String): Flow<List<SatellitesItem>> =
        getSatelliteUseCase.invoke()
            .filterIsInstance<Resource<List<SatellitesItem>>>()
            .map {
                it.data.orEmpty().filter { satellite ->
                    satellite.name.contains(searchString.trim(), ignoreCase = true)
                }
            }
}
