package com.mhmtn.satellites2.domain.useCase.getSatellites

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.FakeSatelliteRepo
import com.mhmtn.satellites2.domain.useCase.getSatelliteDetail.GetSatelliteDetailUseCase
import com.mhmtn.satellites2.util.Resource
import io.mockk.mockk
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class SearchSatelliteUseCaseTest {


    private lateinit var searchSatelliteUseCase: SearchSatelliteUseCase

    @Mock
    private lateinit var getSatelliteUseCase: GetSatelliteUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchSatelliteUseCase = SearchSatelliteUseCase(getSatelliteUseCase)
    }

    @Test
    fun `Search Satellite, correct satellite return`(): Unit = runTest {

        val searchString = "space"

        val satellites = listOf(
            SatellitesItem(true, 1, "Satellite A"),
            SatellitesItem(false, 2, "Satellite B"),
            SatellitesItem(false,3, "Space Station")
        )

        val resource = Resource.Success(satellites)

        whenever(getSatelliteUseCase.invoke()).thenReturn(flowOf(resource))

        val result = searchSatelliteUseCase

        result.invoke(searchString).collect{
            assertEquals(
                it.map { it.name },
                satellites.filter { it.name.contains(searchString,ignoreCase = true) }.map { it.name }
            )
        }
    }
}