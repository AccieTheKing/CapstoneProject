package com.example.capstone.models

import com.google.gson.annotations.SerializedName

data class Announcement(
    @SerializedName("title") var title: String,
    @SerializedName("banner_image") var banner_image: String,
    @SerializedName("text") var text: String,
)