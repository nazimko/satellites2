package com.mhmtn.satellites2.domain.use_case.get_satellite_detail

import android.content.Context
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSatelliteDetailUseCase @Inject constructor(private val repo:SatelliteRepo, private val context: Context) {
/*
    fun executeGetSatelliteDetail (id:Int,) : Flow<Resource<SatelliteDetailItem>> = flow {
        try {
           // emit(Resource.Loading())
            val satellitesDetail = repo.getSatelliteDetail(id)
            emit(Resource.Success(satellitesDetail))
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage ?: "Error."))
        }
    }

 */
}
