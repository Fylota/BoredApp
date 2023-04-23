package com.example.boredapp.di

import android.app.Application
import androidx.room.Room
import com.example.boredapp.R
import com.example.boredapp.persistence.AppDatabase
import com.example.boredapp.persistence.BoredActivityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                application.getString(R.string.database)
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBoredActivityDao(appDatabase: AppDatabase): BoredActivityDao {
        return appDatabase.boredActivityDao()
    }
}