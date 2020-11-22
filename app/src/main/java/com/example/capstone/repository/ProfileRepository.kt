package com.example.capstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.api.ProfileApi
import com.example.capstone.models.Profile
import com.example.capstone.services.ProfileApiService

/**
 * This repository handles the communication in the data access layer and gets the data
 * and provides it to business logic layer.
 */
class ProfileRepository {
    private val profileApiService: ProfileApiService = ProfileApi.createApi()
    private val _profile: MutableLiveData<Profile> = MutableLiveData()

    val profile: LiveData<Profile> get() = _profile

    suspend fun getProfile(phone_number: Int) {
        try {
            val result = profileApiService.getProfile(phone_number)
            _profile.value = result
        } catch (error: Throwable) {
            throw ProfileApiError("Profile api error", error)
        }
    }

    suspend fun updateProfile(phone_number: Int) {
        try {
            val result = profileApiService.updateProfile(phone_number)
            _profile.value = result
        } catch (error: Throwable) {
            throw ProfileApiError("Profile api error", error)
        }
    }

    class ProfileApiError(message: String, cause: Throwable) : Throwable(message, cause)
}