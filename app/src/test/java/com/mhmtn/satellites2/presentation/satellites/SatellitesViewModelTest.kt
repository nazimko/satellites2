package com.mhmtn.satellites2.presentation.satellites

import com.mhmtn.satellites2.domain.repo.FakeSatelliteRepo
import com.mhmtn.satellites2.domain.useCase.getSatellites.GetSatelliteUseCase
import com.mhmtn.satellites2.domain.useCase.getSatellites.SearchSatelliteUseCase
import com.mhmtn.satellites2.testConstants.TestConstants.NEW_SEARCH_KEY
import com.mhmtn.satellites2.testConstants.TestConstants.TEST_SEARCH_KEY
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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

        viewModel.getSatellites()

        val value = viewModel.state.value

        assertEquals(satellites, value.satellites)
        assertEquals(false, value.isLoading)
        assertEquals("", value.error)
    }

    @Test
    fun `makeSearch should update state correctly`() = runTest {
        val key = TEST_SEARCH_KEY
        val satellites = fakeSatelliteRepo.getSatellites().first().data!!
        val mock = mockk<SearchSatelliteUseCase>()
        coEvery { mock.invoke(key) } returns flowOf(satellites)

        viewModel.makeSearch(key)

        val state = viewModel.state.value
        assertEquals(satellites, state.satellites)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `updateSearchKey should update searchKey in state`() {
        val key = NEW_SEARCH_KEY

        viewModel.updateSearchKey(key)

        val state = viewModel.state.value
        assertEquals(key, state.searchKey)
    }
}