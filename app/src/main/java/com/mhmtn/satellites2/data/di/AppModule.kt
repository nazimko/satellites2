package com.mhmtn.satellites2.data.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.mhmtn.satellites2.data.model.SatellitesItem
import com.mhmtn.satellites2.data.repo.SatelliteRepoImpl
import com.mhmtn.satellites2.domain.dataSource.SatelliteDataSource
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import com.mhmtn.satellites2.domain.use_case.get_satellites.SearchSatelliteUseCase
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
    @Singleton
    fun providesSatelliteRepo(dataSource: SatelliteDataSource) : SatelliteRepo{
        return SatelliteRepoImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideContext (application: Application): Context = application.applicationContext
}