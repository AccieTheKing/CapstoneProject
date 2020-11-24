package com.example.capstone.services

import com.example.capstone.models.Product
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService {
    @GET("/products")
    suspend fun getProducts(): List<Product>

    @GET("/products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    @POST("/products/cart/add/{product_id}/{profile_id}")
    suspend fun addProductToCart(
        @Path("product_id") product_id: Int,
        @Path("profile_id") profile_id: String
    ): List<Product>

    @POST("/products/cart/remove/{product_id}/{profile_id}")
    suspend fun removeProductFromCart(
        @Path("product_id") product_id: Int,
        @Path("profile_id") profile_id: Int
    ): List<Product>
}