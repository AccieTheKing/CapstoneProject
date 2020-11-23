package com.example.capstone.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository = ProductRepository()

    val products = productRepository.products
    val product = productRepository.product
    val cart = productRepository.cart

    fun getProducts() {
        viewModelScope.launch {
            productRepository.getProducts()
        }
    }

    fun getProduct(product_id: Int) {
        viewModelScope.launch {
            productRepository.getProduct(product_id)
        }
    }

    fun addProductToCart(product_id: Int) {
        viewModelScope.launch {
            productRepository.addProductToCart(product_id)
        }
    }

    fun removeProductToCart(product_id: Int) {
        viewModelScope.launch {
            productRepository.removeProductToCart(product_id)
        }
    }
}