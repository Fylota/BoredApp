package com.example.boredapp.ui.main

import com.example.boredapp.model.BoredActivity
import com.example.boredapp.network.BoredActivityService
import com.example.boredapp.persistence.BoredActivityDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val boredActivityService: BoredActivityService,
    private val boredActivityDao: BoredActivityDao
) {
    suspend fun getRandomActivity(): BoredActivity? =
        boredActivityService.getRandomActivity(null).body()

    suspend fun getActivityByKey(key: Long): BoredActivity? =
        boredActivityService.getRandomActivity(key).body()

    fun getAllActivitiesFromDB(): Flow<List<BoredActivity>> = boredActivityDao.getAllBoredActivities()

    fun getActivityFromDB(key: Long): Flow<BoredActivity> = boredActivityDao.getBoredActivity(key)

    suspend fun addActivity(activity: BoredActivity) = boredActivityDao.insertBoredActivity(activity)

    suspend fun removeActivity(activity: BoredActivity) = boredActivityDao.deleteBoredActivity(activity)

    suspend fun updateActivity(activity: BoredActivity) = boredActivityDao.updateBoredActivity(activity)

}
