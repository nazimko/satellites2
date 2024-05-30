package com.mhmtn.satellites2.data.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.mhmtn.satellites2.data.local.ParseJson
import com.mhmtn.satellites2.data.repo.SatelliteRepoImpl
import com.mhmtn.satellites2.domain.repo.SatelliteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesSatelliteRepo(api:ParseJson, context:Context) : SatelliteRepo{
        return SatelliteRepoImpl(api, context)
    }

    @Provides
    @Singleton
    fun provideSatelliteApi() = ParseJson()

    @Provides
    @Singleton
    fun provideContext (application: Application): Context = application.applicationContext
}