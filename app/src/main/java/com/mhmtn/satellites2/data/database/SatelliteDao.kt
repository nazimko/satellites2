package com.mhmtn.satellites2.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhmtn.satellites2.data.model.SatelliteDetailItem

@Dao
interface SatelliteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatellite(satellite : SatelliteDetailItem)

    @Query("SELECT * FROM satellitedetailitem WHERE id=:satelliteId")
    suspend fun getSatellite(satelliteId:Int):SatelliteDetailItem?
}