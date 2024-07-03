package com.mhmtn.satellites2.domain.useCase.getSatellites

import app.cash.turbine.test
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.FakeSatelliteRepo
import com.mhmtn.satellites2.testConstants.TestConstants.TEST_ERROR
import com.mhmtn.satellites2.testConstants.TestConstants.TEST_SATELLITE_NAME
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class GetSatelliteUseCaseTest {

    @Mock
    private lateinit var fakeSatelliteRepo: FakeSatelliteRepo

    private lateinit var getSatelliteUseCase: GetSatelliteUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getSatelliteUseCase = GetSatelliteUseCase(fakeSatelliteRepo)
    }

    @Test
    fun `invoke should return a flow of satellites list`() = runTest {

        val satellites = listOf(
            SatellitesItem(true, 1, TEST_SATELLITE_NAME),
            SatellitesItem(false, 2, TEST_SATELLITE_NAME)
        )

        val expectedFlow: Flow<Resource<List<SatellitesItem>>> = flow {
            emit(Resource.Success(satellites))
        }

        `when`(fakeSatelliteRepo.getSatellites()).thenReturn(expectedFlow)

        getSatelliteUseCase().test {
            val result = awaitItem()
            assertTrue(result is Resource.Success)
            assertEquals(satellites, (result as Resource.Success).data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke should return error when repository throws exception`() = runTest {

        val error = TEST_ERROR
        val expectedFlow: Flow<Resource<List<SatellitesItem>>> = flow {
            emit(Resource.Error(error))
        }

        `when`(fakeSatelliteRepo.getSatellites()).thenReturn(expectedFlow)

        getSatelliteUseCase().test {
            val result = awaitItem()
            assertTrue(result is Resource.Error)
            assertEquals(error,(result as Resource.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}