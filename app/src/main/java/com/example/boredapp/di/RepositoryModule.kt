package com.example.boredapp.di

import com.example.boredapp.network.BoredActivityService
import com.example.boredapp.persistence.BoredActivityDao
import com.example.boredapp.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        boredActivityService: BoredActivityService,
        boredActivityDao: BoredActivityDao
    ): MainRepository {
        return MainRepository(boredActivityService, boredActivityDao)
    }
}