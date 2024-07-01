package com.mhmtn.satellites2.presentation.satelliteDetail

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.mhmtn.satellites2.data.model.DetailModel
import com.mhmtn.satellites2.domain.repo.FakeSatelliteRepo
import com.mhmtn.satellites2.domain.useCase.getSatelliteDetail.GetSatelliteDetailUseCase
import com.mhmtn.satellites2.util.Constants.SatelliteID
import com.mhmtn.satellites2.util.Constants.SatelliteName
import com.mhmtn.satellites2.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var fakeSatelliteRepo: FakeSatelliteRepo
    private lateinit var getSatelliteDetailUseCase: GetSatelliteDetailUseCase
    private val mock = mockk<GetSatelliteDetailUseCase>()
    private val savedStateHandle: SavedStateHandle =
        SavedStateHandle(mapOf(SatelliteID to "1", SatelliteName to "TestSat"))
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeSatelliteRepo = FakeSatelliteRepo()
        getSatelliteDetailUseCase = GetSatelliteDetailUseCase(fakeSatelliteRepo)
        viewModel = DetailViewModel(getSatelliteDetailUseCase, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should get satellite detail and update state`() = runTest {

        val detail = fakeSatelliteRepo.getSatelliteDetail(1)
        val position = fakeSatelliteRepo.getPositions(1)

        combine(detail, position) { det, pos ->
            val de = det.data!!
            val po = pos.data!!
            val detailModel = DetailModel(
                id = de.id,
                height = de.height,
                mass = de.mass,
                costPerLaunch = de.costPerLaunch,
                firstFlight = de.firstFlight,
                positions = po
            )
            coEvery { mock.invoke(1) } returns flow { emit(Resource.Success(detailModel)) }
        }
        val state = viewModel.state.first { !it.isLoading }

        assertThat(state.satelliteId).isEqualTo(1)
        assertThat(state.height).isEqualTo(500)
        assertThat(state.mass).isEqualTo(40000)
        assertThat(state.costPerLaunch).isEqualTo(1000)
        assertThat(state.firstFlight).isEqualTo("13.03.1990")
        assertThat(state.position).isEqualTo("Latitude/Longitude")
        assertThat(state.name).isEqualTo("TestSat")
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `getSatelliteDetail should handle error`() = runTest {
        coEvery { mock.invoke(1) } returns flow {emit(Resource.Error(""))}

        val state = viewModel.state.first { !it.isLoading }

        assertThat(state.error).isEqualTo("")
        assertThat(state.isLoading).isFalse()
    }
}

