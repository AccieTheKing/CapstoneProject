package com.example.capstone.api

import com.example.capstone.services.ProductApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductApi {
    companion object {
        // The base url off the api.
        private const val baseUrl = ""

        /**
         * @return [ProductApiService] The service class off the retrofit client.
         */
        fun createApi(): ProductApiService {
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

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