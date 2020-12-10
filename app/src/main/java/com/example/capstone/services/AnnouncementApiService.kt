package com.example.capstone.services

import com.example.capstone.models.Announcement
import retrofit2.http.GET
import retrofit2.http.Path

interface AnnouncementApiService {
    @GET("/announcements")
    suspend fun getAnnouncements(): List<Announcement>

    @GET("/announcement/{announcementID}")
    suspend fun getAnnouncements(@Path(value = "announcementID") announcementID: Int): Announcement
}