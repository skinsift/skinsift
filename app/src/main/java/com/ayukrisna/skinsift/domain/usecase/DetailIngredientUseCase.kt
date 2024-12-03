package com.ayukrisna.skinsift.domain.usecase

import com.ayukrisna.skinsift.data.remote.response.Ingredient
import com.ayukrisna.skinsift.data.remote.response.DetailIngredientsResponse
import com.ayukrisna.skinsift.domain.repository.IngredientRepository
import com.ayukrisna.skinsift.util.Result

class DetailIngredientUseCase(private val detailIngredientsRepository: IngredientRepository) {
    suspend fun execute(id: Int): Result<Ingredient> {
        return try {
            val response = detailIngredientsRepository.getDetailIngredient(id)
            if (response.error == false) {
                val getDetailIngredient = response.data
                if (getDetailIngredient != null) {
                    Result.Success(getDetailIngredient)
                } else {
                    Result.Error("Detail ingredient is null")
                }
            } else {
                Result.Error(response.message ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}