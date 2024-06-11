package com.mhmtn.satellites2.presentation.satelliteDetail

import com.mhmtn.satellites2.data.model.SatelliteDetailItem

data class DetailState (
    var isLoading : Boolean = false,
    var satellite : SatelliteDetailItem? = null,
    val error : String = "",
    var position : String = "",
    var name : String = ""
)