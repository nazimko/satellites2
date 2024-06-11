package com.mhmtn.satellites2.domain.useCase.getPositions

import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPositionsUseCase @Inject constructor(private val repo: SatelliteRepo) {
    suspend fun executeGetPositions(id: Int): Flow<Resource<String>> {
        return repo.getPositions(id)
    }
}