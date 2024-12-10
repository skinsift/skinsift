package com.ayukrisna.skinsift.domain.usecase.article

import com.ayukrisna.skinsift.data.remote.response.article.NewsResultsItem
import com.ayukrisna.skinsift.data.remote.response.article.SerpResponse
import com.ayukrisna.skinsift.domain.repository.ArticleRepository
import com.ayukrisna.skinsift.util.Result

class ArticleUseCase(private val articleRepository: ArticleRepository) {
    suspend fun execute(): Result<List<NewsResultsItem>> {
        return try {
            val response = articleRepository.getNews()
            val getArticleResults = response.newsResults?.filterNotNull()
            if (getArticleResults != null) {
                Result.Success(getArticleResults)
            } else {
                Result.Error("List article is null")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}