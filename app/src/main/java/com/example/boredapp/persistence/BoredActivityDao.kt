package com.example.boredapp.persistence

import androidx.room.*
import com.example.boredapp.model.BoredActivity

@Dao
interface BoredActivityDao {

    @Query("SELECT * from activities ORDER BY activity ASC")
    fun getAllBoredActivities(): List<BoredActivity>

    @Query("SELECT * from activities WHERE key = :key")
    fun getBoredActivity(key: Long): BoredActivity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoredActivityList(boredActivities: List<BoredActivity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBoredActivity(boredActivity: BoredActivity)

    @Update
    suspend fun updateBoredActivity(boredActivity: BoredActivity)

    @Delete
    suspend fun deleteBoredActivity(boredActivity: BoredActivity)
}