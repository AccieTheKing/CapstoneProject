package com.example.capstone.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstone.models.Product
import com.example.capstone.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository = ProductRepository()
    private val _errorText: MutableLiveData<String> = MutableLiveData()

    val products = productRepository.products
    val product = productRepository.product
    val cart = productRepository.cart

    val success = MutableLiveData<Boolean>()
    val errorText: LiveData<String> get() = _errorText

    fun getProducts() {
        viewModelScope.launch {
            try {
                productRepository.getProducts()
                success.value = true
            } catch (error: ProductRepository.ProductError) {
                _errorText.value = error.message
                success.value = false
                Log.e("Getting products error", error.cause.toString())
            }
        }
    }

    fun getProduct(product_id: Int) {
        viewModelScope.launch {
            try {
                productRepository.getProduct(product_id)
            } catch (error: ProductRepository.ProductError) {
                _errorText.value = error.message
                success.value = false
                Log.e("Getting product error", error.cause.toString())
            }
        }
    }

    fun getCart(phoneNumber: Int) {
        viewModelScope.launch {
            try {
                productRepository.getCart(phoneNumber)
            } catch (error: ProductRepository.ProductError) {
                _errorText.value = error.message
                success.value = false
                Log.e("Getting cart error", error.cause.toString())
            }
        }
    }

    fun addProductToCart(product: Product, phone_number: String) {
        viewModelScope.launch {
            try {
                productRepository.addProductToCart(product, phone_number)
            } catch (error: ProductRepository.ProductError) {
                _errorText.value = error.message
                success.value = false
                Log.e("Error product to cart", error.cause.toString())
            }
        }
    }

    fun removeProductToCart(product_id: Int, profile_id: String) {
        viewModelScope.launch {
            try {
                productRepository.removeProductToCart(product_id, profile_id)
            } catch (error: ProductRepository.ProductError) {
                _errorText.value = error.message
                success.value = false
                Log.e("Error product to cart", error.cause.toString())
            }
        }
    }
}