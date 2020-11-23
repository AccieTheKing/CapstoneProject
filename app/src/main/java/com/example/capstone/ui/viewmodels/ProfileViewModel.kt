package com.example.capstone.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val profileRepository = ProfileRepository()

    val profile = profileRepository.profile

    fun getProfile(phone_number: Int) {
        viewModelScope.launch {
            profileRepository.getProfile(phone_number)
        }
    }

    fun updateProfile(phone_number: Int) {
        viewModelScope.launch {
            profileRepository.updateProfile(phone_number)
        }
    }
}