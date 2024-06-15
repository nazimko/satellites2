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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.presentation.satellites.SatellitesState
import com.mhmtn.satellites2.presentation.theme.Gray80
@Composable
fun HomeScreen(
        state : SatellitesState,
        onNavigate : (String) -> Unit,
        makeSearch : (String) -> Unit,
        updateSearchKey : (String) -> Unit
) {

    Box(modifier = Modifier
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
                .padding(16.dp),
                    onSearch = {
                               makeSearch(it)
                    },
                    onValueChange = {
                                    updateSearchKey(it)
                    },
                    searchKey = state.searchKey)

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.satellites) {
                    SatelliteRow(satellite = it, onNavigate = onNavigate )
                    HorizontalDivider(thickness = 0.75.dp,color = Color.Black)
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
            placeholder = {Text(text = "Search")},
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
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(
) {
    HomeScreen(
        state = SatellitesState(
            satellites = listOf(
                SatellitesItem(active = true, id = 1, name = "Satellite1"),
                SatellitesItem(active = false, id = 2, name = "Satellite2"),
                SatellitesItem(active = true, id = 3, name = "Satellite3")
            )
        ) ,
        onNavigate = {},
        makeSearch = {},
        updateSearchKey = {})
}

