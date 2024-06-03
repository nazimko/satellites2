package com.mhmtn.satellites2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mhmtn.satellites2.data.repo.Destination
import com.mhmtn.satellites2.presentation.satellite_detail.views.DetailScreen
import com.mhmtn.satellites2.presentation.satellites.views.HomeScreen
import com.mhmtn.satellites2.presentation.theme.Satellites2Theme
import com.mhmtn.satellites2.util.Constants.SatelliteID
import com.mhmtn.satellites2.util.Constants.SatelliteName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Satellites2Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Destination.HomeScreen.route){

                    composable(Destination.HomeScreen.route){

                        HomeScreen(navController = navController)
                    }

                    composable(Destination.DetailScreen.route+"/{${SatelliteID}}/{${SatelliteName}}"){
                        DetailScreen()
                    }
                }
            }
        }
    }
}