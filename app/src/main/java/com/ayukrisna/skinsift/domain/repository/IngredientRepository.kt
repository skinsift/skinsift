package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.DetailIngredientsResponse
import com.ayukrisna.skinsift.data.remote.response.FilterIngreResponse
import com.ayukrisna.skinsift.data.remote.response.IngredientsResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    fun getSession(): Flow<UserModel>
    suspend fun getIngredients() : IngredientsResponse
    suspend fun searchIngredient(name: String?, rating: List<String>?, benefit: List<String>?) : IngredientsResponse
    suspend fun getDetailIngredient(id: Int) : DetailIngredientsResponse
    suspend fun getFilterIngredient() : FilterIngreResponse
}

