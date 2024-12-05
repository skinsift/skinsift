package com.ayukrisna.skinsift.domain.usecase.product

import com.ayukrisna.skinsift.data.remote.response.product.DetailProduct
import com.ayukrisna.skinsift.domain.repository.ProductRepository
import com.ayukrisna.skinsift.util.Result

class DetailProductUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(id: Int): Result<DetailProduct> {
        return try {
            val response = productRepository.getDetailProduct(id)
            if (response.error == false) {
                val getDetailProduct = response.data
                if (getDetailProduct != null) {
                    Result.Success(getDetailProduct)
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