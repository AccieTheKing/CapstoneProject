package com.example.capstone.models

import com.google.gson.annotations.SerializedName

/**
 * @model Announcement
 *
 * This model is meant to be used on the Home fragment.
 * Here the news of the store or restaurant can be found
 */
data class Announcement(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("banner_image") var banner_image: String,
    @SerializedName("text") var text: String,
)