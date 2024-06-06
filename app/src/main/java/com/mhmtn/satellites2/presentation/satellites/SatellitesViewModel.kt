package com.mhmtn.satellites2.presentation.satellites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.satellites2.domain.use_case.get_satellites.GetSatelliteUseCase
import com.mhmtn.satellites2.domain.use_case.get_satellites.SearchSatelliteUseCase
import com.mhmtn.satellites2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatellitesViewModel @Inject constructor(
        private val useCase: GetSatelliteUseCase,
        private val searchUseCase: SearchSatelliteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SatellitesState())
    val state: StateFlow<SatellitesState> = _state.asStateFlow()

    init {
        getSatellites()
    }

    private fun getSatellites() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        useCase.executeGetSatellites().collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                                error = resource.message.orEmpty(),
                                isLoading = false)
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                                satellites = resource.data.orEmpty(),
                                isLoading = false)
                    }
                }
            }
        }
    }

    fun startSearch(searchString: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        searchUseCase.executeSearchSatellite(searchString)
                .collect { list ->
                    _state.update { it.copy(satellites = list, isLoading = false) }
                }
    }

    fun updateSearchKey(searchKey: String) {
        _state.update {
            it.copy(searchKey = searchKey)
        }
    }
}