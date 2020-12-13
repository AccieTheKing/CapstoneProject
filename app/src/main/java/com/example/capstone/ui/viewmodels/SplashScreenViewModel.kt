package com.example.capstone.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.repository.SplashRepository
import kotlinx.coroutines.launch

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val splashRepository = SplashRepository()
    val splashDetails = splashRepository.splashDetails

    fun getSplashDetails() {
        viewModelScope.launch {
            splashRepository.getSplashDetails()
        }
    }
}