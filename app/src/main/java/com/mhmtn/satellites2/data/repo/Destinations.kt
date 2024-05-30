package com.mhmtn.satellites2.data.repo

sealed class Destination (val route: String) {

    object HomeScreen : Destination("HomeScreen")
    object DetailScreen : Destination("DetailScreen")
}