package com.ayukrisna.skinsift.domain.usecase.product

import com.ayukrisna.skinsift.data.remote.response.product.ProductFilter
import com.ayukrisna.skinsift.domain.repository.ProductRepository
import com.ayukrisna.skinsift.util.Result

class FilterProductUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(): Result<ProductFilter> {
        return try {
            val response = productRepository.getFilterProduct()
            if (response.error == false) {
                val getFilterProduct = response.data?.firstOrNull()
                if (getFilterProduct != null) {
                    Result.Success(getFilterProduct)
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