package com.example.capstone.services

import com.example.capstone.models.Announcement
import retrofit2.http.GET

interface AnnouncementApiService {
    @GET("/announcements")
    suspend fun getAnnouncements(): List<Announcement>
}