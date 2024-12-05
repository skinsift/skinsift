package com.ayukrisna.skinsift.domain.usecase.ingredient

import com.ayukrisna.skinsift.data.remote.response.ingredients.Filter
import com.ayukrisna.skinsift.domain.repository.IngredientRepository
import com.ayukrisna.skinsift.util.Result

class FilterIngredientUseCase(private val ingredientRepository: IngredientRepository) {
    suspend fun execute(): Result<Filter> {
        return try {
            val response = ingredientRepository.getFilterIngredient()
            if (response.error == false) {
                val getFilterIngredient = response.data
                if (getFilterIngredient != null) {
                    Result.Success(getFilterIngredient)
                } else {
                    Result.Error("Filter is null")
                }
            } else {
                Result.Error(response.message ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}