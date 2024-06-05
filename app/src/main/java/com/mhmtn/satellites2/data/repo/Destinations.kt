package com.mhmtn.satellites2.data.repo

import com.mhmtn.satellites2.util.Constants.SatelliteID
import com.mhmtn.satellites2.util.Constants.SatelliteName

sealed class Destination (val route: String) {

    data object HomeScreen : Destination("HomeScreen")
    data object DetailScreen : Destination("DetailScreen/{${SatelliteID}}/{${SatelliteName}}"){
        fun createRoute(id: Int, name: String) = "DetailScreen/$id/$name"
    }
}