package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.ProductResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getSession(): Flow<UserModel>
    suspend fun getProducts() : ProductResponse
}