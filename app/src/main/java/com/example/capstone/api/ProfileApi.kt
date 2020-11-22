package com.example.capstone.api

import com.example.capstone.services.ProfileApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileApi {
    companion object {
        // The base url off the api.
        private const val baseUrl = ""

        /**
         * @return [ProfileApiService] The service class off the retrofit client.
         */
        fun createApi(): ProfileApiService {
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val profileApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit ProfileApiService
            return profileApi.create(ProfileApiService::class.java)
        }
    }
}