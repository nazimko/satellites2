package com.mhmtn.satellites2.presentation.satellite_detail

import com.mhmtn.satellites2.data.model.PositionsItem

data class PositionState (
    val isLoading : Boolean = false,
    var position : PositionsItem? = null,
    val error : String = ""
)