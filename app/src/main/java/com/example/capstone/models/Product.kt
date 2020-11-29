package com.example.capstone.models

import com.google.gson.annotations.SerializedName

/**
 * @model Product
 *
 * This model is going to be used on the menu fragment and the user can add them
 * to a list and eventually buy them
 */
data class Product(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("banner_image") var banner_image: String,
    @SerializedName("description") var description: String,
    @SerializedName("amount") var amount: Int,
    @SerializedName("price") var price: Double,
)