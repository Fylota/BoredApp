package com.example.boredapp.network

import com.example.boredapp.model.BoredActivity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BoredActivityService {
    @GET("/activity")
    suspend fun getRandomActivity(@Query("key") key: Long?): Response<BoredActivity>
}
