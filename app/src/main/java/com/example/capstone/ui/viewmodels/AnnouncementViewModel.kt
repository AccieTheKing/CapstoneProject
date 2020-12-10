package com.example.capstone.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.repository.AnnouncementRepository
import kotlinx.coroutines.launch

class AnnouncementViewModel(application: Application) : AndroidViewModel(application) {
    private val announcementRepository = AnnouncementRepository()

    val announcements = announcementRepository.announcements
    val announcement = announcementRepository.announcement

    fun getAnnouncements() {
        viewModelScope.launch {
            announcementRepository.getAnnouncements()
        }
    }

    fun getAnnouncementDetails(id: Int) {
        viewModelScope.launch {
            announcementRepository.getAnnouncement(id)
        }
    }
}