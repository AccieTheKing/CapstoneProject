package com.example.capstone.api

import com.example.capstone.services.ProductApiService
import com.example.capstone.services.ProfileApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiObject {
    companion object {
        // The base url off the api.
        private const val baseUrl = "http://192.168.178.199:5000/"
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        fun createProfileApi(): ProfileApiService {
            // Create the Retrofit instance
            val profileApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit ProfileApiService
            return profileApi.create(ProfileApiService::class.java)
        }

        fun createProductApi(): ProductApiService {
            // Create the Retrofit instance
            val productApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit ProductApiService
            return productApi.create(ProductApiService::class.java)
        }
    }
}