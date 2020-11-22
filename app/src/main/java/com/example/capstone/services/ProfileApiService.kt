package com.example.capstone.services

import com.example.capstone.models.Profile
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileApiService {
    @GET("/profile/{phone_number}")
    suspend fun getProfile(@Path("phone_number") phone_number: Int): Profile

    @POST("/profile/update/{phone_number}")
    suspend fun updateProfile(@Path("phone_number") phone_number: Int): Profile
}