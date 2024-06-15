package com.mhmtn.satellites2.presentation.satellites.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.data.repo.Destination
import com.mhmtn.satellites2.presentation.theme.Gray80

@Composable
fun SatelliteRow(
    satellite: SatellitesItem,
    onNavigate : (String) -> Unit
) {

    Row(horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = Gray80)
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                onNavigate(
                    Destination.DetailScreen.createRoute(
                        satellite.id,
                        satellite.name
                )
                )
            }) {
        Canvas(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .padding(16.dp)
        ) {
            drawCircle(
                color = if (satellite.active) {
                    Color.Green
                } else Color.Red,
                radius = 40f
            )
        }

        Column {
            Text(text = satellite.name, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = if (satellite.active) {
                    "Active"
                } else "Passive"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}