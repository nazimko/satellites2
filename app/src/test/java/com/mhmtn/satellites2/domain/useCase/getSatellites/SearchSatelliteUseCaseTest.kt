package com.mhmtn.satellites2.domain.useCase.getSatellites

import android.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.testConstants.TestConstants.TEST_ERROR
import com.mhmtn.satellites2.testConstants.TestConstants.TEST_SEARCH_KEY
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SearchSatelliteUseCaseTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var searchSatelliteUseCase: SearchSatelliteUseCase

    @Mock
    private lateinit var getSatelliteUseCase: GetSatelliteUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchSatelliteUseCase = SearchSatelliteUseCase(getSatelliteUseCase)
    }

    @Test
    fun `invoke should filter satellites by name`(): Unit = runTest {

        val searchString = TEST_SEARCH_KEY

        val satellites = listOf(
            SatellitesItem(true, 1, "Satellite A"),
            SatellitesItem(false, 2, "Satellite B"),
            SatellitesItem(false,3, "Space Station")
        )

        val resource = Resource.Success(satellites)

        whenever(getSatelliteUseCase.invoke()).thenReturn(flowOf(resource))

        searchSatelliteUseCase.invoke(searchString).test {
            assertEquals(awaitItem(), satellites.filter { it.name.contains(searchString, ignoreCase = true) })
            awaitComplete()
        }
        verify(getSatelliteUseCase).invoke()
    }


    @Test
    fun `invoke should handle empty list`() = runTest() {

        val searchString = TEST_SEARCH_KEY

        val resource = Resource.Success(emptyList<SatellitesItem>())

        whenever(getSatelliteUseCase.invoke()).thenReturn(flowOf(resource))

        searchSatelliteUseCase.invoke(searchString).test {
            assertEquals(awaitItem(), emptyList<SatellitesItem>())
            awaitComplete()
        }
        verify(getSatelliteUseCase).invoke()
    }

    @Test
    fun `invoke should handle error`() = runTest {

        val searchString = TEST_SEARCH_KEY
        val error = TEST_ERROR
        val resource = Resource.Error<List<SatellitesItem>>(error)

        whenever(getSatelliteUseCase.invoke()).thenReturn(flow {
            emit(resource)
            throw Exception(error)
        })

        searchSatelliteUseCase.invoke(searchString).catch {
            assertEquals(it.message, error)
        }
        verify(getSatelliteUseCase).invoke()
    }
}