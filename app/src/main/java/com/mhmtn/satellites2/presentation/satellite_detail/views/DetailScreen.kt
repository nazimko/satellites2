package com.mhmtn.satellites2.presentation.satellite_detail.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mhmtn.satellites2.data.model.PositionsItem
import com.mhmtn.satellites2.presentation.satellite_detail.DetailViewModel
import com.mhmtn.satellites2.presentation.theme.Gray80
import com.mhmtn.satellites2.util.Resource

@Composable
fun DetailScreen(
    viewModel : DetailViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val name by viewModel.name.collectAsState()
    val position by viewModel.positionState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray80),
        contentAlignment = Alignment.Center
    ) {
        state.satellite?.let {
            Column( verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Gray80)) {
                Text(text = name, fontWeight = FontWeight.Bold, fontSize = 32.sp)
                Text(text = it.first_flight, fontWeight = FontWeight.Thin)
                Spacer(modifier = Modifier.height(48.dp))
                DetailRow(text = "Height/Mass:", detail1 = it.height, detail2 = it.mass)
                Spacer(modifier = Modifier.height(16.dp))
                DetailRow(text = "Cost:", detail1 =it.cost_per_launch, detail2 =null )
                Text(text = position.position)
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