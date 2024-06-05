package com.mhmtn.satellites2.data.model

import com.google.gson.annotations.SerializedName

data class PositionsItem(
    @SerializedName("id") val id: String,
    @SerializedName("positions") val positions: List<Position>
)

data class DataList(
    @SerializedName("list") val list: List<PositionsItem>
)