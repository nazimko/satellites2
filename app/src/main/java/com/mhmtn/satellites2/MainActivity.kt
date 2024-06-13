package com.mhmtn.satellites2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mhmtn.satellites2.data.model.SatelliteDetailEntity
import com.mhmtn.satellites2.data.repo.Destination
import com.mhmtn.satellites2.presentation.satelliteDetail.DetailViewModel
import com.mhmtn.satellites2.presentation.satelliteDetail.views.DetailScreen
import com.mhmtn.satellites2.presentation.satellites.SatellitesViewModel
import com.mhmtn.satellites2.presentation.satellites.views.HomeScreen
import com.mhmtn.satellites2.presentation.theme.Satellites2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Satellites2Theme {
                val navController = rememberNavController()

                NavHost(
                        navController = navController,
                        startDestination = Destination.HomeScreen.route) {
                    composable(Destination.HomeScreen.route) {
                        val viewModel: SatellitesViewModel = hiltViewModel()
                        val state by viewModel.state.collectAsState()
                        HomeScreen(
                            state = state,
                            onNavigate = navController::navigate,
                            makeSearch = viewModel::makeSearch,
                            updateSearchKey = viewModel::updateSearchKey
                            )
                    }
                    composable(Destination.DetailScreen.route) {
                        val viewModel : DetailViewModel = hiltViewModel()
                        val state by viewModel.state.collectAsState()
                        DetailScreen(
                            state = state
                        )
                    }
                }
            }
        }
    }
}