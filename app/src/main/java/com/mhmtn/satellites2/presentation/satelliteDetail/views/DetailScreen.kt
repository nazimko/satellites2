package com.mhmtn.satellites2.presentation.satelliteDetail.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.presentation.satelliteDetail.DetailState
import com.mhmtn.satellites2.presentation.satelliteDetail.DetailViewModel
import com.mhmtn.satellites2.presentation.satellites.SatellitesState
import com.mhmtn.satellites2.presentation.satellites.views.HomeScreen
import com.mhmtn.satellites2.presentation.theme.Gray80

@Composable
fun DetailScreen(
    state : DetailState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray80),
        contentAlignment = Alignment.Center
    ) {
        Column( verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray80)) {
            Text(text = state.name, fontWeight = FontWeight.Bold, fontSize = 32.sp)
            Text(text = state.firstFlight, fontWeight = FontWeight.Thin)
            Spacer(modifier = Modifier.height(48.dp))
            DetailRow(text = "Height/Mass:", detail1 = state.height, detail2 = state.mass)
            Spacer(modifier = Modifier.height(16.dp))
            DetailRow(text = "Cost:", detail1 =state.costPerLaunch, detail2 =null )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = "Last Position: " , fontWeight = FontWeight.Bold)
                Text(text = "(${state.position})")
            }
        }
        if (state.error.isNotBlank()){
            Text(
                text = state.error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .align(Alignment.Center))
        }
        if (state.isLoading){
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(
) {
    DetailScreen(
        state = DetailState(
            name = "Satellite",
            height = 1000,
            mass = 10000,
            costPerLaunch = 100000,
            firstFlight = "GG/AA/YYYY",
            position = "Latitude-Longitude"
        )
    )
}