package com.mhmtn.satellites2.domain.useCase.getSatellites

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.mhmtn.satellites2.domain.repo.FakeSatelliteRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSatelliteUseCaseTest {

    private lateinit var getSatelliteUseCase: GetSatelliteUseCase
    private lateinit var fakeSatelliteRepo: FakeSatelliteRepo

    @Before
    fun setUp() {
        fakeSatelliteRepo = FakeSatelliteRepo()
        getSatelliteUseCase = GetSatelliteUseCase(fakeSatelliteRepo)
    }

    @Test
    fun `Get Satellite List, correct satellite list return`(): Unit = runBlocking {
        val satellites = getSatelliteUseCase.invoke().first()
        assertThat(satellites.data?.get(0)?.name).isEqualTo("Satellite 1")
    }

    @Test
    fun `Get Satellite List, incorrect satellite list return`(): Unit = runBlocking {
        val satellites = getSatelliteUseCase.invoke().first()
        assertThat(satellites.data?.get(0)?.name).isNotEqualTo("Satellite 2")
    }
}