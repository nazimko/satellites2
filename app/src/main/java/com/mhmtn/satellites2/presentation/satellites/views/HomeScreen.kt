package com.mhmtn.satellites2.presentation.satellites.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mhmtn.satellites2.presentation.satellites.SatellitesViewModel
import com.mhmtn.satellites2.presentation.theme.Gray80

@Composable
fun HomeScreen(
        navController: NavController,
        viewModel: SatellitesViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .background(Gray80),
            contentAlignment = Alignment.TopCenter
    ) {

        Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchBar(modifier = Modifier
                    .background(color = Gray80)
                    .padding(16.dp), onSearch = { searchKey ->
                viewModel.startSearch(searchString = searchKey)
            }, onValueChange = { searchKey ->
                viewModel.updateSearchKey(searchKey)
            }, searchKey = state.searchKey)

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.satellites) {
                    SatelliteRow(navController = navController, satellite = it)
                    Divider(color = Color.Black)
                }
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
                            .align(Alignment.Center))
        }


        if (state.isLoading) {
            CircularProgressIndicator()
        }

    }
}

@Composable
fun SearchBar(
        modifier: Modifier = Modifier,
        onSearch: (String) -> Unit = {},
        onValueChange: (String) -> Unit = {},
        searchKey: String,
) {

    Box(modifier = modifier) {
        TextField(value = searchKey, onValueChange = onValueChange,
                keyboardActions = KeyboardActions(onDone = { onSearch(searchKey) }),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.tertiary),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text(text = "Search") },
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .background(color = Color.White, CircleShape),
                colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                    )
                }
        )
    }
}
