package com.mhmtn.satellites2.data.model

import com.google.gson.annotations.SerializedName

data class Position(
    @SerializedName("posX") val posX: Double,
    @SerializedName("posY") val posY: Double
)