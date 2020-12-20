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

    @POST("/product/cart/add/{product_id}/{phoneNumber}")
    suspend fun addProductToCart(
        @Path("product_id") product_id: Int,
        @Path("phoneNumber") phoneNumber: String
    ): List<Product>


    /**
     * Incremental and decremental endpoints
     */
    @POST("/product/cart/increase/{product_id}/{phoneNumber}")
    suspend fun increaseProductAmount(
        @Path("product_id") product_id: Int,
        @Path("phoneNumber") phoneNumber: String
    ): List<Product>


    @POST("/product/cart/decrease/{product_id}/{phoneNumber}")
    suspend fun decreaseProductAmount(
        @Path("product_id") product_id: Int,
        @Path("phoneNumber") phoneNumber: String
    ): List<Product>
}