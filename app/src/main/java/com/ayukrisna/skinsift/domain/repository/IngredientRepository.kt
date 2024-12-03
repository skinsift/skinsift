package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.IngredientsResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    fun getSession(): Flow<UserModel>
    suspend fun getIngredients() : IngredientsResponse
}