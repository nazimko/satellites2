package com.mhmtn.satellites2.presentation.satellite_detail

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmtn.satellites2.data.database.SatelliteDatabase
import com.mhmtn.satellites2.data.model.PositionsItem
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import com.mhmtn.satellites2.domain.use_case.get_positions.GetPositionsUseCase
import com.mhmtn.satellites2.domain.use_case.get_satellite_detail.GetSatelliteDetailUseCase
import com.mhmtn.satellites2.presentation.satellites.SatellitesState
import com.mhmtn.satellites2.util.Constants.SatelliteID
import com.mhmtn.satellites2.util.Constants.SatelliteName
import com.mhmtn.satellites2.util.Resource
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: GetSatelliteDetailUseCase,
    private val positionsUseCase: GetPositionsUseCase,
    private val stateHandle: SavedStateHandle,
    @SuppressLint("StaticFieldLeak") private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _positionState = MutableStateFlow(PositionState())
    val positionState: StateFlow<PositionState> = _positionState.asStateFlow()

    private val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    private val isSaved = sharedPreferences.getBoolean("isSaved",false)
    private val id= sharedPreferences.getInt("id",0)

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
                    if (isSaved && id==this@DetailViewModel.id){
                        _state.update { it.copy(satellite = getDataFromDB(id), isLoading = false) }
                    } else {
                        _state.update { it.copy(satellite = res.data!!, isLoading = false) }
                        store(res.data!!)
                        sharedPreferences.edit().putBoolean("isSaved",true).apply()
                        sharedPreferences.edit().putInt("id",id).apply()
                        Toast.makeText(context,"Data From API",Toast.LENGTH_LONG).show()
                    }
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
    private suspend fun store(satelliteDetail : SatelliteDetailItem){
        val dao = SatelliteDatabase(context = context).satelliteDao()
        dao.insertSatellite(satelliteDetail)
        Toast.makeText(context,"Saved to DB",Toast.LENGTH_LONG).show()
    }
    private suspend fun getDataFromDB (id:Int) : SatelliteDetailItem? {
        val detailItem = SatelliteDatabase(context).satelliteDao().getSatellite(id)
        Toast.makeText(context,"Data From DB",Toast.LENGTH_LONG).show()
        return detailItem
    }
}