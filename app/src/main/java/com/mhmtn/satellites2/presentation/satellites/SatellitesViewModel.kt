package com.mhmtn.satellites2.presentation.satellites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.use_case.get_satellites.GetSatelliteUseCase
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
    private val useCase : GetSatelliteUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SatellitesState())
    val state : StateFlow<SatellitesState> = _state.asStateFlow()

    private var asilListe = listOf<SatellitesItem>()
    private var isSearchStarting = true

    init {
        getSatellites()
    }

    private fun getSatellites() = viewModelScope.launch{
        useCase.executeGetSatellites().onStart { _state.update { it.copy(isLoading = true) } }.collect {
            when(it){
                is Resource.Error -> {
                    _state.value = SatellitesState(error = it.message.orEmpty())
                }

                is Resource.Success -> {
                    _state.value = SatellitesState(satellites = it.data ?: emptyList())
                }
            }
        }
    }



    fun onEvent (event : SatellitesEvent){
        when (event){
            is SatellitesEvent.Search -> {
                val listToSearch = if (isSearchStarting){
                    _state.value.satellites
                }else{
                    asilListe
                }

                viewModelScope.launch (Dispatchers.Default) {

                    if (event.searchString.isEmpty()){
                        _state.value = SatellitesState(satellites = asilListe)
                        isSearchStarting = true
                        return@launch
                    }

                    val results = listToSearch.filter {
                        it.name.contains(event.searchString.trim(),ignoreCase = true)
                    }

                    if (isSearchStarting){
                        asilListe = _state.value.satellites
                        isSearchStarting=false
                    }
                    _state.value=SatellitesState(satellites = results)
                }
            }
        }
    }
}