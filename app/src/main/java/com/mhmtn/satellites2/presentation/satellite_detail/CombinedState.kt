package com.mhmtn.satellites2.presentation.satellite_detail

data class CombinedState (
    var detailState: DetailState = DetailState(),
    var name: String = "",
    var positionState: PositionState = PositionState()
)