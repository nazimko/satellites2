package com.mhmtn.satellites2.presentation.satelliteDetail.views

import android.content.Context
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhmtn.satellites2.presentation.satelliteDetail.DetailState
import com.mhmtn.satellites2.presentation.theme.Gray80
import com.mhmtn.satellites2.R

@Composable
fun DetailScreen(
    state: DetailState
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray80),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray80)
        ) {
            Text(text = state.name, fontWeight = FontWeight.Bold, fontSize = 32.sp)
            Text(text = state.firstFlight, fontWeight = FontWeight.Thin)
            Spacer(modifier = Modifier.height(48.dp))
            DetailRow(text = stringResource(id = R.string.height_mass), detail1 = state.height, detail2 = state.mass)
            Spacer(modifier = Modifier.height(16.dp))
            DetailRow(text = stringResource(id = R.string.cost), detail1 = state.costPerLaunch, detail2 = null)
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(text = stringResource(id = R.string.last_position), fontWeight = FontWeight.Bold)
                Text(text = "(${state.position})")
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
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