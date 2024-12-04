package com.ayukrisna.skinsift.data.repository

import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.remote.request.SearchIngredientRequest
import com.ayukrisna.skinsift.data.remote.response.DetailIngredientsResponse
import com.ayukrisna.skinsift.data.remote.response.FilterIngreResponse
import com.ayukrisna.skinsift.data.remote.response.IngredientsResponse
import com.ayukrisna.skinsift.data.remote.retrofit.ApiConfig
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.IngredientRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class IngredientRepositoryImp (private val userPreference: UserPreference) : IngredientRepository {
    override fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    override suspend fun getIngredients(): IngredientsResponse {
        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.getIngredients()

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    override suspend fun getDetailIngredient(id: Int): DetailIngredientsResponse {
        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.getDetailIngredients(id)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    override suspend fun searchIngredient(
        name: String?,
        rating: List<String>?,
        benefit: List<String>?
    ): IngredientsResponse {
        val searchQuery = SearchIngredientRequest(name, rating, benefit)

        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.searchIngredient(searchQuery)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    override suspend fun getFilterIngredient(): FilterIngreResponse {
        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.getFilterIngredient()

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    private fun parseErrorBody(errorBody: String): IngredientsResponse? {
        return try {
            Gson().fromJson(errorBody, IngredientsResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }
}