package com.example.capstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.api.Api
import com.example.capstone.models.Product
import com.example.capstone.services.ProductApiService

class ProductRepository {
    private val productApiService: ProductApiService = Api.createProductApi()

    private val _product: MutableLiveData<Product> = MutableLiveData() // single products
    private val _products: MutableLiveData<List<Product>> =
        MutableLiveData() // list of all products
    private val _cart: MutableLiveData<List<Product>> = MutableLiveData() // user cart
    private val _price: MutableLiveData<Double> = MutableLiveData() // price of total products

    val product: LiveData<Product> get() = _product
    val products: LiveData<List<Product>> get() = _products
    val cart: LiveData<List<Product>> get() = _cart
    val price: LiveData<Double> get() = _price


    /**
     * This method will give a list of products available
     */
    suspend fun getProducts() {
        try {
            val result = productApiService.getProducts()
            _products.value = result
        } catch (error: Throwable) {
            throw ProductError("Something went wrong with getting the products", error)
        }
    }

    /**
     * This method will give a specific product based on the id given
     */
    suspend fun getProduct(product_id: Int) {
        try {
            val result = productApiService.getProduct(product_id)
            _product.value = result
        } catch (error: Throwable) {
            throw ProductError("Getting the single product failed", error)
        }
    }

    /**
     * This method will add a product to the user cart and return the list
     * without this product
     */
    suspend fun addProductToCart(product: Product, phone_number: String) {
        try {
            val result = productApiService.addProductToCart(product.id, phone_number)
            _cart.value = result
            _price.value = calculateTotalPrice(result)
        } catch (error: Throwable) {
            throw ProductError("Adding the product to cart failed", error)
        }
    }

    suspend fun increaseProductAmount(product_id: Int, phoneNumber: String) {
        try {
            val result = productApiService.increaseProductAmount(product_id, phoneNumber)
            _cart.value = result
            _price.value = calculateTotalPrice(result)
        } catch (error: Throwable) {
            throw ProductError("Increasing the product amount failed", error)
        }
    }

    suspend fun decreaseProductAmount(product_id: Int, phoneNumber: String) {
        try {
            val result = productApiService.decreaseProductAmount(product_id, phoneNumber)
            _cart.value = result
            _price.value = calculateTotalPrice(result)
        } catch (error: Throwable) {
            throw ProductError("Decreasing the product amount", error)
        }
    }


    suspend fun getCart(phoneNumber: Int) {
        try {
            val result = productApiService.getCart(phoneNumber)
            _cart.value = result
            _price.value = calculateTotalPrice(result)
        } catch (error: Throwable) {
            throw ProductError("Getting the cart failed", error)
        }
    }

    private fun calculateTotalPrice(cart: List<Product>): Double {
        var totalPrice = 0.00
        cart.forEach { product ->
            if (product.price.toString().isNotEmpty()) {
                totalPrice += product.price
            }
        }
        return totalPrice
    }

    class ProductError(message: String, cause: Throwable) : Throwable(message, cause)
}