package com.ayukrisna.skinsift.domain.usecase.product

import com.ayukrisna.skinsift.data.remote.response.IngredientListItem
import com.ayukrisna.skinsift.data.remote.response.ProductListItem
import com.ayukrisna.skinsift.domain.repository.ProductRepository
import com.ayukrisna.skinsift.util.Result

class ProductUseCase (private val productRepository: ProductRepository) {
    suspend fun execute(): Result<List<ProductListItem>> {
        return try {
            val response = productRepository.getProducts()
            if (response.error == false) {
                val getProductResults = response.productList?.filterNotNull()
                if (getProductResults != null) {
                    Result.Success(getProductResults)
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
