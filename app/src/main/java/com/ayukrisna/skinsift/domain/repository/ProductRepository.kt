package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.product.DetailProductResponse
import com.ayukrisna.skinsift.data.remote.response.product.FilterProductResponse
import com.ayukrisna.skinsift.data.remote.response.product.ProductResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getSession(): Flow<UserModel>
    suspend fun getProducts() : ProductResponse
    suspend fun getDetailProduct(id: Int): DetailProductResponse
    suspend fun getFilterProduct(): FilterProductResponse
    suspend fun searchProduct(query: String?, skinType: List<String>?, category: List<String>?) : ProductResponse
}