package com.example.capstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.api.Api
import com.example.capstone.models.Profile
import com.example.capstone.services.ProfileApiService

/**
 * This repository handles the communication in the data access layer and gets the data
 * and provides it to business logic layer.
 */
class ProfileRepository {
    private val profileApiService: ProfileApiService = Api.createProfileApi()
    private val _profile: MutableLiveData<Profile> = MutableLiveData()
    private val _success: MutableLiveData<Boolean> = MutableLiveData()

    companion object {
        var phoneNumber: String = ""
        var emailAddress: String = ""
        var authToken: String = ""
    }

    val profile: LiveData<Profile> get() = _profile
    val success: LiveData<Boolean> get() = _success

    suspend fun getProfile() {
        try {
            val result = profileApiService.getProfile()
            _profile.value = result
        } catch (error: Throwable) {
            throw ProfileApiError("Profile fetching error", error)
        }
    }

    suspend fun updateProfile(phone_number: String, email_address: String) {
        try {
            val result = profileApiService.updateProfile(
                ProfileApiService.SendUser(
                    phone_number,
                    email_address
                )
            )
            _profile.value = result
            phoneNumber = phone_number
            emailAddress = email_address
        } catch (error: Throwable) {
            throw ProfileApiError("Profile updating error", error)
        }
    }

    suspend fun getVerificationCode(phone_number: String, email_address: String) {
        try {
            profileApiService.getVerificationCode(
                ProfileApiService.SendUser(
                    phone_number, email_address
                )
            )
            phoneNumber = phone_number
            emailAddress = email_address
        } catch (error: Throwable) {
            throw ProfileApiError("Sending verification code error", error)
        }
    }

    suspend fun sendVerificationCode(code: Int, phone_number: String, email_address: String) {
        try {
            val result = profileApiService.sendVerificationCode(
                ProfileApiService.SendVerificationCode(
                    verificationCode = code,
                    phone_number = phone_number,
                    email_address = email_address
                )
            )

            phoneNumber = phone_number
            emailAddress = email_address

            if (result.authToken !== null && result.msg == null) {
                authToken = result.authToken!!
                _success.value = true
            } else if (result.authToken == null && result.msg !== null) {
                _success.value = false
            } else {
                _success.value = false
            }
        } catch (error: Throwable) {
            throw ProfileApiError("Invalid verification code!", error)
        }
    }

    class ProfileApiError(message: String, cause: Throwable) : Throwable(message, cause)
}