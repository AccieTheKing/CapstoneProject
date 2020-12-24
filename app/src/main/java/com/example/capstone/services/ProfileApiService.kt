package com.example.capstone.services

import com.example.capstone.models.Profile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileApiService {
    @GET("/profile")
    suspend fun getProfile(): Profile

    @POST("/profile/save")
    suspend fun updateProfile(@Body profile: SendUser): Profile

    @POST("/profile/getverificationcode")
    suspend fun getVerificationCode(@Body profile: SendUser): String

    @POST("/profile/sendverificationcode")
    suspend fun sendVerificationCode(@Body code: SendVerificationCode): BackendResponseMessage

    data class BackendResponseMessage(var msg: String?, var authToken: String?)

    data class SendVerificationCode(
        var verificationCode: Int,
        var phone_number: String,
        var email_address: String
    )

    data class SendUser(
        var phone_number: String,
        var email_address: String
    )
}