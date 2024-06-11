package com.mhmtn.satellites2.presentation.satellites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.satellites2.domain.useCase.getSatellites.GetSatelliteUseCase
import com.mhmtn.satellites2.domain.useCase.getSatellites.SearchSatelliteUseCase
import com.mhmtn.satellites2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatellitesViewModel @Inject constructor(
    private val useCase : GetSatelliteUseCase,
    private val searchUseCase : SearchSatelliteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SatellitesState())
    val state : StateFlow<SatellitesState> = _state.asStateFlow()

    private var job : Job? = null

    init {
        getSatellites()
    }

    private fun getSatellites() = viewModelScope.launch{
        useCase.executeGetSatellites().onStart {
            _state.update { it.copy(isLoading = true) }
        }.collect {resource->
            when(resource){
                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message.orEmpty(), isLoading = false) }
                }
                is Resource.Success -> {
                    _state.update { it.copy(satellites = resource.data ?: emptyList(), isLoading = false) }
                }
            }
        }
    }

    fun onEvent (event : SatellitesEvent){
        when (event){
            is SatellitesEvent.Search -> {
                job?.cancel()
                job = viewModelScope.launch (Dispatchers.Default) {
                    searchUseCase.executeSearchSatellite(event.searchString).onStart{
                        _state.update { it.copy(isLoading = true) }
                    }.collect{ list->
                        _state.update { it.copy(satellites = list, isLoading = false) }
                    }
                }
            }
        }
    }
}