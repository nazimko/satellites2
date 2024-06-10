package com.mhmtn.satellites2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SatelliteDetailItem(
    @ColumnInfo(name = "cost")
    val cost_per_launch: Int,
    @ColumnInfo(name = "firstFlight")
    val first_flight: String,
    @ColumnInfo(name = "height")
    val height: Int,
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "mass")
    val mass: Int
)