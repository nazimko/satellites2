package com.mhmtn.satellites2.presentation.satellites

import com.mhmtn.satellites2.domain.repo.FakeSatelliteRepo
import com.mhmtn.satellites2.domain.useCase.getSatellites.GetSatelliteUseCase
import com.mhmtn.satellites2.domain.useCase.getSatellites.SearchSatelliteUseCase
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain


@ExperimentalCoroutinesApi
class SatellitesViewModelTest {

    private lateinit var getSatelliteUseCase: GetSatelliteUseCase
    private lateinit var fakeSatelliteRepo: FakeSatelliteRepo
    private lateinit var searchSatelliteUseCase: SearchSatelliteUseCase
    private lateinit var viewModel: SatellitesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeSatelliteRepo = FakeSatelliteRepo()
        getSatelliteUseCase = GetSatelliteUseCase(fakeSatelliteRepo)
        searchSatelliteUseCase = SearchSatelliteUseCase(getSatelliteUseCase)
        viewModel = SatellitesViewModel(
            useCase = getSatelliteUseCase,
            searchUseCase = searchSatelliteUseCase
        )
    }

    @Test
    fun `getSatellites should update state with satellites on success`() = runTest {

        val satellitesFlow = fakeSatelliteRepo.getSatellites()
        val satellites = satellitesFlow.first().data!!
        val mock = mockk<GetSatelliteUseCase>()

        coEvery { mock.invoke() } returns satellitesFlow

        val getSatellitesMethod = viewModel.javaClass.getDeclaredMethod("getSatellites")
        getSatellitesMethod.isAccessible = true
        getSatellitesMethod.invoke(viewModel)

        val value = viewModel.state.value

        assertEquals(satellites, value.satellites)
        assertEquals(false, value.isLoading)
        assertEquals("", value.error)
    }
}