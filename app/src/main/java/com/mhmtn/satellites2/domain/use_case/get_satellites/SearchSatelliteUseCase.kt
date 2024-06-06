package com.mhmtn.satellites2.domain.use_case.get_satellites

import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchSatelliteUseCase @Inject constructor(
        private val getSatelliteUseCase: GetSatelliteUseCase
) {
    suspend fun executeSearchSatellite(searchString: String): Flow<List<SatellitesItem>> =
            getSatelliteUseCase.executeGetSatellites()
                    .filterIsInstance<Resource.Success<List<SatellitesItem>>>()
                    .map {
                        it.data.orEmpty().filter { satellite ->
                            satellite.name.contains(searchString.trim(), ignoreCase = true)
                        }
                    }
}
