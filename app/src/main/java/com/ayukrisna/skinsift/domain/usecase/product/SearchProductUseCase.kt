package com.ayukrisna.skinsift.domain.usecase.product

import com.ayukrisna.skinsift.data.remote.response.IngredientListItem
import com.ayukrisna.skinsift.data.remote.response.ProductListItem
import com.ayukrisna.skinsift.domain.repository.ProductRepository
import com.ayukrisna.skinsift.util.Result

class SearchProductUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(query: String? = null, skinType: List<String>? = null, category: List<String>? = null): Result<List<ProductListItem>> {
        return try {
            val response = productRepository.searchProduct(query = query, skinType = skinType, category = category)
            if (response.error == false) {
                val getProductResult = response.productList?.filterNotNull()
                if (getProductResult != null) {
                    Result.Success(getProductResult)
                } else {
                    Result.Error("List Product is null")
                }
            } else {
                Result.Error(response.message ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}