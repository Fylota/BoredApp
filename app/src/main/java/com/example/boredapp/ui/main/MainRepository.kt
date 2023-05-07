package com.example.boredapp.ui.main

import com.example.boredapp.network.BoredActivityService
import com.example.boredapp.persistence.BoredActivityDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val boredActivityService: BoredActivityService,
    private val boredActivityDao: BoredActivityDao
) {
    suspend fun getRandomActivity() = boredActivityService.getRandomActivity(null)
    suspend fun getActivityByKey(key: Long) = boredActivityService.getRandomActivity(key)
}
