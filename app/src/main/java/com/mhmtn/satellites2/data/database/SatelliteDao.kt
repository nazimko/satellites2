package com.mhmtn.satellites2.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhmtn.satellites2.data.model.SatelliteDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SatelliteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatellite(satellite: SatelliteDetailEntity)

    @Query("SELECT * FROM satellitedetailentity WHERE id=:satelliteId")
    fun getSatellite(satelliteId: Int): Flow<SatelliteDetailEntity?>

    @Query("SELECT EXISTS(SELECT * FROM satellitedetailentity WHERE id=:satelliteId)")
    fun checkItemIsExist(satelliteId: Int): Flow<Boolean>
}