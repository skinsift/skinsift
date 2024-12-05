package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.ingredients.DetailIngredientsResponse
import com.ayukrisna.skinsift.data.remote.response.ingredients.FilterIngreResponse
import com.ayukrisna.skinsift.data.remote.response.ingredients.IngredientsResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    fun getSession(): Flow<UserModel>
    suspend fun getIngredients() : IngredientsResponse
    suspend fun searchIngredient(query: String?, rating: List<String>?, benefit: List<String>?) : IngredientsResponse
    suspend fun getDetailIngredient(id: Int) : DetailIngredientsResponse
    suspend fun getFilterIngredient() : FilterIngreResponse
}

