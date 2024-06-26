package com.mhmtn.satellites2.domain.useCase.getSatellites

import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSatelliteUseCase @Inject constructor(private val repo: SatelliteRepo) {
    operator fun invoke(): Flow<Resource<List<SatellitesItem>>> {
        return repo.getSatellites()
    }
}