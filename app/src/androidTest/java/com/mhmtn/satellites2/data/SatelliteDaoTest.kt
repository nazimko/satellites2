package com.mhmtn.satellites2.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule
import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.mhmtn.satellites2.data.database.SatelliteDao
import com.mhmtn.satellites2.data.database.SatelliteDatabase
import com.mhmtn.satellites2.data.model.SatelliteDetailEntity
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SatelliteDaoTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var dao: SatelliteDao
    private lateinit var database: SatelliteDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SatelliteDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.satelliteDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insert_and_get_correct_satellite() = runTest {

        val mockSatellite = SatelliteDetailEntity(
            1000,
            "13.13.1990",
            450,
            326,
            50
        )
        dao.insertSatellite(mockSatellite)
        val insertedSatellite = dao.getSatellite(326).first()!!

        assertNotNull(insertedSatellite)
        assertThat(mockSatellite.id).isEqualTo(insertedSatellite.id)
        assertThat(mockSatellite.firstFlight).isEqualTo(insertedSatellite.firstFlight)
        assertThat(mockSatellite.height).isEqualTo(insertedSatellite.height)
        assertThat(mockSatellite.mass).isEqualTo(insertedSatellite.mass)
        assertThat(mockSatellite.costPerLaunch).isEqualTo(insertedSatellite.costPerLaunch)
    }

    @Test
    fun insert_and_get_incorrect_satellite() = runTest {

        val mockSatellite = SatelliteDetailEntity(
            1000,
            "13.13.1990",
            450,
            326,
            50
        )
        dao.insertSatellite(mockSatellite)
        val insertedSatellite = dao.getSatellite(326).first()!!
        assertThat(insertedSatellite.id).isNotEqualTo(236)
    }

    @Test
    fun check_item_is_exist() = runTest {

        val mockSatellite = SatelliteDetailEntity(
            1000,
            "13.13.1990",
            450,
            250,
            50
        )
        dao.insertSatellite(mockSatellite)
        val isExist = dao.checkItemIsExist(250).first()
        assertThat(isExist).isTrue()
    }

    @Test
    fun check_item_is_not_exist() = runTest {

        val isExist = dao.checkItemIsExist(100).first()
        assertThat(isExist).isFalse()
    }
}