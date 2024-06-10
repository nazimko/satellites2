package com.mhmtn.satellites2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class SatellitesItem(
    val active: Boolean,
    val id: Int,
    val name: String
)