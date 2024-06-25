package com.mhmtn.satellites2.presentation.satelliteDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.satellites2.domain.useCase.getSatelliteDetail.GetSatelliteDetailUseCase
import com.mhmtn.satellites2.util.Constants.SatelliteID
import com.mhmtn.satellites2.util.Constants.SatelliteName
import com.mhmtn.satellites2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: GetSatelliteDetailUseCase,
    private val stateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    init {
        stateHandle.get<String>(SatelliteID)?.let {
            getSatelliteDetail(it.toInt())
        }
        _state.update { it.copy(name = stateHandle.get<String>(SatelliteName).toString()) }
    }

    private fun getSatelliteDetail(id: Int) = viewModelScope.launch {
        useCase.executeGetSatelliteDetail(id = id).onStart {
            _state.update { it.copy(isLoading = true) }
        }.collect { res ->
            when (res) {
                is Resource.Error -> {
                    _state.update { it.copy(error = res.message.orEmpty(), isLoading = false) }
                }

                is Resource.Success -> {
                    val data = res.data!!
                    _state.update {
                        it.copy(
                            satelliteId = data.id,
                            height = data.height,
                            mass = data.mass,
                            costPerLaunch = data.costPerLaunch,
                            firstFlight = data.firstFlight,
                            position = data.positions,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}