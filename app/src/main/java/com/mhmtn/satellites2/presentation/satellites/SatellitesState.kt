package com.mhmtn.satellites2.presentation.satellites

import com.mhmtn.satellites2.data.model.SatellitesItem

data class SatellitesState(
        val isLoading: Boolean = false,
        var satellites: List<SatellitesItem> = emptyList(),
        val error: String = "",
        val searchKey: String = " "
)