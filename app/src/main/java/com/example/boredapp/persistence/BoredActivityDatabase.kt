package com.example.boredapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.boredapp.model.BoredActivity

@Database(entities = [BoredActivity::class], version = 1, exportSchema = true)
abstract class BoredActivityDatabase : RoomDatabase() {
    abstract fun boredActivityDao(): BoredActivityDao
}