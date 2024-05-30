package com.mhmtn.satellites2.presentation.satellites

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.satellites2.domain.use_case.get_satellites.GetSatelliteUseCase
import com.mhmtn.satellites2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SatellitesViewModel @Inject constructor(
    private val useCase : GetSatelliteUseCase,
    application: Application
) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    val context: Context = getApplication<Application>().applicationContext

    private val _state = MutableStateFlow(SatellitesState())
    val state : StateFlow<SatellitesState> = _state.asStateFlow()

    init {
        getSatellites(context)
    }

    private fun getSatellites(context1: Context){
        useCase.executeGetSatellites(context = context1).onEach {
            when(it){
                is Resource.Error -> {
                    _state.value = SatellitesState(error = it.message ?: "Error.")
                }

                is Resource.Success -> {
                    _state.value = SatellitesState(satelltes = it.data ?: emptyList())
                }

                is Resource.Loading -> {
                    _state.value = SatellitesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    /*
    fun onEvent (event : SatellitesEvent){
        when (event){
            is SatellitesEvent.Search -> {

            }
        }
    }

     */
}