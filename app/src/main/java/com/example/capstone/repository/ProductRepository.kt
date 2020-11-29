package com.example.capstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.api.ProductApi
import com.example.capstone.models.Product
import com.example.capstone.models.Profile
import com.example.capstone.services.ProductApiService

class ProductRepository {
    private val productApiService: ProductApiService = ProductApi.createApi()

    private val _product: MutableLiveData<Product> = MutableLiveData() // single products
    private val _products: MutableLiveData<List<Product>> =
        MutableLiveData() // list of all products
    private val _cart: MutableLiveData<List<Product>> = MutableLiveData() // user cart

    val product: LiveData<Product> get() = _product
    val products: LiveData<List<Product>> get() = _products
    val cart: LiveData<List<Product>> get() = _cart

    /**
     * This method will give a list of products available
     */
    suspend fun getProducts() {
        val result = productApiService.getProducts()
        _products.value = result
    }

    /**
     * This method will give a specific product based on the id given
     */
    suspend fun getProduct(product_id: Int) {
        val result = productApiService.getProduct(product_id)
        _product.value = result
    }

    /**
     * This method will add a product to the user cart and return the list
     * without this product
     */
    suspend fun addProductToCart(product: Product, profile: Profile) {
        val result = productApiService.addProductToCart(product.id, profile.phone_number)
        _cart.value = result
    }

    /**
     * This method will remove a product out of the user cart and return the list
     * without this product
     */
    suspend fun removeProductToCart(product_id: Int, profile_id: Int) {
        val result = productApiService.removeProductFromCart(product_id, profile_id)
        _cart.value = result
    }

    suspend fun getCart() {
        val result = productApiService.getCart(0)
        _cart.value = result
    }
}