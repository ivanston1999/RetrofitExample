package com.catnip.retrofitexample.data.repostiory

import com.catnip.retrofitexample.R
import com.catnip.retrofitexample.api.ProductService
import com.catnip.retrofitexample.model.Product
import com.catnip.retrofitexample.utils.ResultWrapper
import com.catnip.retrofitexample.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProduct(): Flow<ResultWrapper<List<Product>>>
}

class ProductRepositoryImpl():ProductRepository{
    private val productService = ProductService()

    override suspend fun getProduct(): Flow<ResultWrapper<List<Product>>> {
        return proceedFlow {
            val productsResponse = productService.getProducts()
            productsResponse.products
        }
    }

}