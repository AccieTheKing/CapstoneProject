package com.example.capstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.api.Api
import com.example.capstone.models.Splash
import com.example.capstone.services.SplashApiService

class SplashRepository {
    private val splashApiService: SplashApiService = Api.createSplashApi()
    private val _splashDetails: MutableLiveData<Splash> = MutableLiveData()

    val splashDetails: LiveData<Splash> get() = _splashDetails

    suspend fun getSplashDetails() {
        val result = splashApiService.getSplashDetails()
        _splashDetails.value = result
    }
}