package com.mhmtn.satellites2.presentation.satellite_detail

import com.mhmtn.satellites2.data.model.PositionsItem
import com.mhmtn.satellites2.data.model.SatelliteDetailItem

data class DetailState (
    val isLoading : Boolean = false,
    var satellite : SatelliteDetailItem? = null,
    val error : String = "",
)