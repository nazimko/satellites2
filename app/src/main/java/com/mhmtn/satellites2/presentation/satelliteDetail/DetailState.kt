package com.mhmtn.satellites2.presentation.satelliteDetail

data class DetailState(
    val isLoading: Boolean = false,
    val error: String = "",
    val position: String = "",
    val name: String = "",
    val satelliteId: Int = 0,
    val costPerLaunch: Int = 0,
    val firstFlight: String = "",
    val height: Int = 0,
    val mass: Int = 0,
)