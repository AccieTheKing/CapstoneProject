package com.example.capstone.services

import com.example.capstone.models.Product
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService {
    @GET("/product")
    suspend fun getProducts(): List<Product>

    @GET("/product/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product

    @GET("/product/cart/{id}")
    suspend fun getCart(@Path("id") id: Int): List<Product>

    @POST("/product/cart/add/{product_id}/{profile_id}")
    suspend fun addProductToCart(
        @Path("product_id") product_id: Int,
        @Path("profile_id") profile_id: String
    ): List<Product>

    @POST("/product/cart/remove/{product_id}/{profile_id}")
    suspend fun removeProductFromCart(
        @Path("product_id") product_id: Int,
        @Path("profile_id") profile_id: Int
    ): List<Product>
}