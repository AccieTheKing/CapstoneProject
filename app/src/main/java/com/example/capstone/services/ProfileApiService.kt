package com.example.capstone.services

import com.example.capstone.models.Profile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileApiService {
    @GET("/profile/{phone_number}")
    suspend fun getProfile(@Path("phone_number") phone_number: String): Profile

    @POST("/profile/save")
    suspend fun updateProfile(@Body profile: SendUser): Profile

    data class SendUser(
        var phone_number: String,
        var email_address: String
    )
}