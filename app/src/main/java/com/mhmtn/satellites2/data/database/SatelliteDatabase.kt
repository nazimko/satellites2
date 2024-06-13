package com.mhmtn.satellites2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mhmtn.satellites2.data.model.SatelliteDetailEntity

@Database(entities = [SatelliteDetailEntity::class], version = 2)
abstract class SatelliteDatabase : RoomDatabase() {
    abstract fun satelliteDao():SatelliteDao
}