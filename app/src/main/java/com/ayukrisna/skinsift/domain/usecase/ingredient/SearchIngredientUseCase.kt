package com.ayukrisna.skinsift.domain.usecase.ingredient

import com.ayukrisna.skinsift.data.remote.response.IngredientListItem
import com.ayukrisna.skinsift.domain.repository.IngredientRepository
import com.ayukrisna.skinsift.util.Result

class SearchIngredientUseCase(private val ingredientRepository: IngredientRepository) {
    suspend fun execute(name: String? = null, rating: List<String>? = null, benefit: List<String>? = null): Result<List<IngredientListItem>> {
        return try {
            val response = ingredientRepository.searchIngredient(name, rating, benefit)
            if (response.error == false) {
                val getIngredientsResult = response.ingredientlist?.filterNotNull()
                if (getIngredientsResult != null) {
                    Result.Success(getIngredientsResult)
                } else {
                    Result.Error("List Ingredients is null")
                }
            } else {
                Result.Error(response.message ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}