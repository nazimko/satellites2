package com.mhmtn.satellites2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhmtn.satellites2.data.model.SatelliteDetailEntity

@Database(entities = [SatelliteDetailEntity::class], version = 2)
abstract class SatelliteDatabase : RoomDatabase() {
    abstract fun satelliteDao():SatelliteDao
}