package com.mhmtn.satellites2.domain.useCase.getSatelliteDetail

import com.mhmtn.satellites2.domain.repo.FakeSatelliteRepo
import com.mhmtn.satellites2.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetSatelliteDetailUseCaseTest {

    private lateinit var getSatelliteDetailUseCase: GetSatelliteDetailUseCase
    private lateinit var fakeSatelliteRepo: FakeSatelliteRepo

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        fakeSatelliteRepo = FakeSatelliteRepo()
        getSatelliteDetailUseCase = GetSatelliteDetailUseCase(fakeSatelliteRepo)
    }

    @Test
    fun `invoke returns success when both detail and positions are success`() = runBlocking {

        val id = 1
        val details = fakeSatelliteRepo.getSatelliteDetail(id).first().data!!
        val positions = fakeSatelliteRepo.getPositions(id).first().data!!

        val mock = mockk<FakeSatelliteRepo>()

        coEvery { mock.getSatelliteDetail(id) } returns flowOf(Resource.Success(details))
        every { mock.getPositions(id) } returns flowOf(Resource.Success(positions))

        val result = getSatelliteDetailUseCase.invoke(id).first()

        assertTrue(result is Resource.Success)
        result as Resource.Success

        assertEquals(id, result.data!!.id)
        assertEquals(details.costPerLaunch, result.data!!.costPerLaunch)
        assertEquals(details.height, result.data!!.height)
        assertEquals(details.mass, result.data!!.mass)
        assertEquals(details.firstFlight, result.data!!.firstFlight)
        assertEquals(positions, result.data!!.positions)
    }
}