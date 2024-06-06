package com.mhmtn.satellites2.presentation.satellite_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.satellites2.data.model.PositionsItem
import com.mhmtn.satellites2.domain.use_case.get_positions.GetPositionsUseCase
import com.mhmtn.satellites2.domain.use_case.get_satellite_detail.GetSatelliteDetailUseCase
import com.mhmtn.satellites2.presentation.satellites.SatellitesState
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
    private val positionsUseCase: GetPositionsUseCase,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _positionState = MutableStateFlow(PositionState())
    val positionState: StateFlow<PositionState> = _positionState.asStateFlow()

    init {
        stateHandle.get<String>(SatelliteID)?.let {
            getPositions(it.toInt())
            getSatelliteDetail(it.toInt())
        }
        _name.value = stateHandle.get<String>(SatelliteName).toString()
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
                    _state.update { it.copy(satellite = res.data!!, isLoading = false) }
                }
            }
        }
    }

    private fun getPositions(id: Int) = viewModelScope.launch {
        positionsUseCase.executeGetPositions(id = id).onStart {
            _positionState.update { it.copy(isLoading = true) }
        }.collect { res ->
            when (res) {
                is Resource.Error -> {
                    _positionState.update {
                        it.copy(
                            error = res.message.orEmpty(),
                            isLoading = false
                        )
                    }
                }

                is Resource.Success -> {
                    _positionState.update { it.copy(position = res.data!!, isLoading = false) }
                }
            }
        }
    }
}