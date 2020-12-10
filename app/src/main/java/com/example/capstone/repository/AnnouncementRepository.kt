package com.example.capstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.api.Api
import com.example.capstone.models.Announcement
import com.example.capstone.services.AnnouncementApiService

class AnnouncementRepository {
    private val announcementApiService: AnnouncementApiService = Api.createAnnouncementApi()
    private val _announcements: MutableLiveData<List<Announcement>> = MutableLiveData()
    private val _announcement: MutableLiveData<Announcement> = MutableLiveData()

    val announcements: LiveData<List<Announcement>> get() = _announcements
    val announcement: LiveData<Announcement> get() = _announcement

    suspend fun getAnnouncements() {
        val result = announcementApiService.getAnnouncements()
        _announcements.value = result
    }

    suspend fun getAnnouncement(id: Int) {
        val result = announcementApiService.getAnnouncements(id)
        _announcement.value = result
    }
}