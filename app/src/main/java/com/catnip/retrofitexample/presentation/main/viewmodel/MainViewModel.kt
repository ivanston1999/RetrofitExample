package com.catnip.retrofitexample.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.retrofitexample.data.repostiory.ProductRepository
import com.catnip.retrofitexample.data.repostiory.ProductRepositoryImpl
import com.catnip.retrofitexample.model.Product
import com.catnip.retrofitexample.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val productRepository: ProductRepository = ProductRepositoryImpl(),
) : ViewModel() {

    private val _productState = MutableStateFlow<ResultWrapper<List<Product>>>(ResultWrapper.Loading())
    val productState: StateFlow<ResultWrapper<List<Product>>> = _productState

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProduct().collect { result ->
                _productState.value = result
            }
        }
    }


}