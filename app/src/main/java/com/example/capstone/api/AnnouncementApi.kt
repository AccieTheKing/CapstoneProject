package com.example.capstone.api

import com.example.capstone.services.AnnouncementApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AnnouncementApi {
    // The base url off the api.
    private const val baseUrl = ""

    /**
     * @return [AnnouncementApiService] The service class off the retrofit client.
     */
    fun createApi(): AnnouncementApiService {
        // Create an OkHttpClient to be able to make a log of the network traffic
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        // Create the Retrofit instance
        val announcementApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Return the Retrofit AnnouncementApiService
        return announcementApi.create(AnnouncementApiService::class.java)
    }
}