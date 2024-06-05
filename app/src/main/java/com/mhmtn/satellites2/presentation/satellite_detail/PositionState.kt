package com.mhmtn.satellites2.presentation.satellite_detail

import com.mhmtn.satellites2.data.model.Position

data class PositionState (
    val isLoading : Boolean = false,
    var position : String = "",
    val error : String = ""
)