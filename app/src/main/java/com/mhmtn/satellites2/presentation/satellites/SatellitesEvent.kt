package com.mhmtn.satellites2.presentation.satellites

sealed class SatellitesEvent {
    data class Search(val searchString: String):SatellitesEvent()
}