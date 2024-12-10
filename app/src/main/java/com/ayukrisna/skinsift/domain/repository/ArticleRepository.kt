package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.article.SerpResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getSession(): Flow<UserModel>
    suspend fun getNews(): SerpResponse
}