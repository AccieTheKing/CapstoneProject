package com.example.capstone.api

import com.example.capstone.BuildConfig
import com.example.capstone.repository.ProfileRepository
import com.example.capstone.services.AnnouncementApiService
import com.example.capstone.services.ProductApiService
import com.example.capstone.services.ProfileApiService
import com.example.capstone.services.SplashApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    companion object {
        private const val baseUrl: String = BuildConfig.DEVELOPMENT_SERVER_API
        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original: Request = chain.request()
                    val request: Request = original.newBuilder()
                        .header("Authorization", "Bearer ${ProfileRepository.authToken}")
                        .method(original.method, original.body)
                        .build()
                    return chain.proceed(request)
                }
            })
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        private val retroFitBuilder = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())

        fun createAnnouncementApi(): AnnouncementApiService {
            // Create the Retrofit instance
            val announcementApi = retroFitBuilder.build()
            // Return the Retrofit AnnouncementApiService
            return announcementApi.create(AnnouncementApiService::class.java)
        }

        fun createProfileApi(): ProfileApiService {
            // Create the Retrofit instance
            val profileApi = retroFitBuilder.build()
            // Return the Retrofit ProfileApiService
            return profileApi.create(ProfileApiService::class.java)
        }

        fun createProductApi(): ProductApiService {
            // Create the Retrofit instance
            val productApi = retroFitBuilder.build()
            // Return the Retrofit ProductApiService
            return productApi.create(ProductApiService::class.java)
        }

        fun createSplashApi(): SplashApiService {
            // Create the Retrofit instance
            val splashApi = retroFitBuilder.build()
            // Return the Retrofit SplashApiService
            return splashApi.create(SplashApiService::class.java)
        }
    }
}