package com.mhmtn.satellites2.domain.use_case.get_positions

import com.mhmtn.satellites2.data.model.PositionsItem
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPositionsUseCase @Inject constructor(private val repo: SatelliteRepo) {
    suspend fun executeGetPositions(id: Int): Flow<Resource<String>> {
        return repo.getPositions(id)
    }
}