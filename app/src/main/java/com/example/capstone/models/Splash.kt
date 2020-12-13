package com.example.capstone.models

import com.google.gson.annotations.SerializedName

data class Splash(
    @SerializedName("title") var title: String,
    @SerializedName("credits") var credits: String,
    @SerializedName("background") var background: String,
)
