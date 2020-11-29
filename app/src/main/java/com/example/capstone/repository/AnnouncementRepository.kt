package com.example.capstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.api.AnnouncementApi
import com.example.capstone.models.Announcement
import com.example.capstone.services.AnnouncementApiService

class AnnouncementRepository {
    private val announcementApiService: AnnouncementApiService = AnnouncementApi.createApi()
    private val _announcements: MutableLiveData<List<Announcement>> = MutableLiveData()

    val announcements: LiveData<List<Announcement>> get() = _announcements

    suspend fun getAnnouncements() {
        val result = announcementApiService.getAnnouncements()
        _announcements.value = result
    }
}