package com.mhmtn.satellites2.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mhmtn.satellites2.data.database.SatelliteDao
import com.mhmtn.satellites2.data.database.SatelliteDatabase
import com.mhmtn.satellites2.data.repo.SatelliteRepoImpl
import com.mhmtn.satellites2.domain.dataSource.SatelliteDataSource
import com.mhmtn.satellites2.domain.dataSource.SatelliteLocalDataSource
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideSatellitesDataSource(@ApplicationContext context: Context): SatelliteDataSource {
        return SatelliteDataSource(context)
    }

    @Provides
    fun provideSatelliteDao(satelliteDB: SatelliteDatabase): SatelliteDao {
        return satelliteDB.satelliteDao()
    }

    @Provides
    @Singleton
    fun providesSatelliteRepo(
        dataSource: SatelliteDataSource,
        localDataSource: SatelliteLocalDataSource
    ): SatelliteRepo {
        return SatelliteRepoImpl(dataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideSatelliteDB(@ApplicationContext context: Context): SatelliteDatabase {
        return Room.databaseBuilder(
            context,
            SatelliteDatabase::class.java,
            "satelliteDB"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
    }
}