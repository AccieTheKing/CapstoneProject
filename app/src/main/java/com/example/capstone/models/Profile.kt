package com.example.capstone.models

import com.google.gson.annotations.SerializedName

/**
 * @model Profile
 *
 * This model is meant to be used on the Profile fragment.
 * This model is going to hold the user data, like backgrounds and profile pictures and ofcourse
 * the email address
 */
data class Profile(
    @SerializedName("phone_number") var phone_number: String,
    @SerializedName("email_address") var email_address: String,
    @SerializedName("profile_picture") var profile_picture: String,
    @SerializedName("profile_background") var profile_background: String,
)