package com.example.capstone.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstone.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val profileRepository = ProfileRepository()
    private val _errorText: MutableLiveData<String> = MutableLiveData()

    val profile = profileRepository.profile
    val success = profileRepository.success
    val errorText: LiveData<String> get() = _errorText

    fun getProfile() {
        viewModelScope.launch {
            try {
                profileRepository.getProfile()
            } catch (error: ProfileRepository.ProfileApiError) {
                _errorText.value = error.message
                Log.e("Getting profile error", error.cause.toString())
            }
        }
    }

    fun updateProfile(phone_number: String, email_address: String) {
        viewModelScope.launch {
            try {
                profileRepository.updateProfile(phone_number, email_address)
            } catch (error: ProfileRepository.ProfileApiError) {
                _errorText.value = error.message
                Log.e("Updating profile error", error.cause.toString())
            }
        }
    }

    fun getVerificationCode(phone_number: String, email_address: String) {
        viewModelScope.launch {
            try {
                profileRepository.getVerificationCode(phone_number, email_address)
            } catch (error: ProfileRepository.ProfileApiError) {
                _errorText.value = error.message
                Log.e("Wrong verification code", error.cause.toString())
            }
        }
    }

    fun sendVerificationCode(code: String, phone_number: String, email_address: String) {
        viewModelScope.launch {
            try {
                profileRepository.sendVerificationCode(code.toInt(), phone_number, email_address)
            } catch (error: ProfileRepository.ProfileApiError) {
                _errorText.value = error.message
                Log.e("Wrong verification code", error.cause.toString())
            }
        }
    }
}