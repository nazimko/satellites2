package com.mhmtn.satellites2.data.model

data class DetailModel(
    val id: Int,
    val costPerLaunch: Int,
    val height: Int,
    val mass: Int,
    val firstFlight: String,
    val positions: String
)