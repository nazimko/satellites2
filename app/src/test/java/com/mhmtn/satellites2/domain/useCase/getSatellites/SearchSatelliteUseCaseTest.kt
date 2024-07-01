package com.mhmtn.satellites2.domain.useCase.getSatellites

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.domain.repo.FakeSatelliteRepo
import com.mhmtn.satellites2.domain.useCase.getSatelliteDetail.GetSatelliteDetailUseCase
import com.mhmtn.satellites2.util.Resource
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchSatelliteUseCaseTest {

    private lateinit var searchSatelliteUseCase: SearchSatelliteUseCase
    private lateinit var fakeSatelliteRepo: FakeSatelliteRepo
    private lateinit var getSatelliteUseCase: GetSatelliteUseCase

    @Before
    fun setUp() {
        fakeSatelliteRepo = FakeSatelliteRepo()
        getSatelliteUseCase = GetSatelliteUseCase(fakeSatelliteRepo)
        searchSatelliteUseCase = SearchSatelliteUseCase(getSatelliteUseCase)
    }

    @Test
    fun `Search Satellite, correct satellite return`(): Unit = runBlocking {
        val satellites = searchSatelliteUseCase.invoke("1").first()
        assertThat(satellites.get(0).name).isEqualTo("Satellite 1")
    }

    @Test
    fun `Search Satellite, incorrect satellite return`(): Unit = runTest {
        val satellites = searchSatelliteUseCase.invoke("1").first()
        assertThat(satellites.get(0).name).isNotEqualTo("Satellite 2")
    }
}