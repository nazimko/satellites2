package com.mhmtn.satellites2.domain.useCase.getSatelliteDetail

import com.google.common.truth.Truth.assertThat
import com.mhmtn.satellites2.domain.repo.FakeSatelliteRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSatelliteDetailUseCaseTest {

    private lateinit var getSatelliteDetailUseCase: GetSatelliteDetailUseCase
    private lateinit var fakeSatelliteRepo: FakeSatelliteRepo

    @Before
    fun setUp() {
        fakeSatelliteRepo = FakeSatelliteRepo()
        getSatelliteDetailUseCase = GetSatelliteDetailUseCase(fakeSatelliteRepo)
    }

    @Test
    fun `Get Satellite Details, correct satellite id return`(): Unit = runBlocking {
        val satelliteDetails = getSatelliteDetailUseCase.invoke(1).first()
        assertThat((satelliteDetails.data?.id)).isEqualTo(1)
    }

    @Test
    fun `Get Satellite Details, incorrect satellite id return`(): Unit = runBlocking {
        val satelliteDetails = getSatelliteDetailUseCase.invoke(1).first()
        assertThat((satelliteDetails.data?.id)).isNotEqualTo(2)
    }
}