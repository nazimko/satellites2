package com.mhmtn.satellites2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SatelliteDetailEntity(
    @ColumnInfo(name = "cost")
    @SerializedName("cost_per_launch")
    val costPerLaunch: Int,
    @ColumnInfo(name = "firstFlight")
    @SerializedName("first_flight")
    val firstFlight: String,
    @ColumnInfo(name = "height")
    val height: Int,
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "mass")
    val mass: Int
)
