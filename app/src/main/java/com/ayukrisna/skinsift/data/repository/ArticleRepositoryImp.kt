package com.ayukrisna.skinsift.data.repository

import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.remote.response.article.SerpResponse
import com.ayukrisna.skinsift.data.remote.retrofit.serpApi.SerpApiConfig
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


class ArticleRepositoryImp (private val userPreference: UserPreference) : ArticleRepository {
    override fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    override suspend fun getNews(): SerpResponse {
        val token = userPreference.getSession().first().token
        val apiService = SerpApiConfig.getSerpApiService(token)

        val response = apiService.getNews()

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorMessage = errorBody ?: "HTTP ${response.code()} error"
            throw Exception(errorMessage)
        }
    }
}