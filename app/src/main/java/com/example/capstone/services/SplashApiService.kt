package com.example.capstone.services

import com.example.capstone.models.Splash
import retrofit2.http.GET

interface SplashApiService {
    @GET("/splashscreen")
    suspend fun getSplashDetails(): Splash
}