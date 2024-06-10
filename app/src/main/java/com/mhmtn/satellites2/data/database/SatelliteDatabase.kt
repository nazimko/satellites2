package com.mhmtn.satellites2.data.database

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mhmtn.satellites2.data.model.SatelliteDetailItem
import javax.inject.Inject

@Database(entities = [SatelliteDetailItem::class], version = 1)
abstract class SatelliteDatabase : RoomDatabase() {

    abstract fun satelliteDao():SatelliteDao

    companion object {
       @Volatile private var instance : SatelliteDatabase ? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context = context).also {
                instance = it
            }
        }

        private fun makeDatabase (context: Context) = Room.databaseBuilder(
            context.applicationContext,SatelliteDatabase::class.java,"satelliteDB"
        ).build()
    }
}