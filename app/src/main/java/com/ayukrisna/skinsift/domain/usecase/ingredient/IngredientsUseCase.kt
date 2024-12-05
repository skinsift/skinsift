package com.ayukrisna.skinsift.domain.usecase.ingredient

import com.ayukrisna.skinsift.data.remote.response.ingredients.IngredientListItem
import com.ayukrisna.skinsift.domain.repository.IngredientRepository
import com.ayukrisna.skinsift.util.Result

class IngredientsUseCase (private val ingredientRepository: IngredientRepository){
    suspend fun execute(): Result<List<IngredientListItem>> {
        return try {
            val response = ingredientRepository.getIngredients()
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