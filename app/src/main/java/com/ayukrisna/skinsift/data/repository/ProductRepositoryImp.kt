package com.ayukrisna.skinsift.data.repository

import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.remote.request.SearchProductRequest
import com.ayukrisna.skinsift.data.remote.response.product.DetailProductResponse
import com.ayukrisna.skinsift.data.remote.response.product.FilterProductResponse
import com.ayukrisna.skinsift.data.remote.response.product.ProductResponse
import com.ayukrisna.skinsift.data.remote.retrofit.api.ApiConfig
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.ProductRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ProductRepositoryImp (private val userPreference: UserPreference) : ProductRepository {
    override fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    override suspend fun getProducts(): ProductResponse {
        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.getProducts()

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    override suspend fun getDetailProduct(id: Int): DetailProductResponse {
        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.getDetailProduct(id)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    override suspend fun getFilterProduct(): FilterProductResponse {
        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.getFilterProduct()

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    override suspend fun searchProduct(
        query: String?,
        skinType: List<String>?,
        category: List<String>?
    ): ProductResponse {
        val searchQuery = SearchProductRequest(query, category, skinType)

        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.searchProduct(searchQuery)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    private fun parseErrorBody(errorBody: String): ProductResponse? {
        return try {
            Gson().fromJson(errorBody, ProductResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }
}