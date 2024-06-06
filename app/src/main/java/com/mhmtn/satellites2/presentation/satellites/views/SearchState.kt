package com.mhmtn.satellites2.presentation.satellites.views

import com.mhmtn.satellites2.data.model.SatellitesItem

data class SearchState(
    val satellites : List<SatellitesItem> = emptyList()
)