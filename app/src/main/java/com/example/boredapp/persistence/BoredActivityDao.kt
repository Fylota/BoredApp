package com.example.boredapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.boredapp.model.BoredActivity

@Dao
interface BoredActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoredActivityList(boredActivities: List<BoredActivity>)
}