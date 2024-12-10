package com.ayukrisna.skinsift.view.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.article.NewsResultsItem
import com.ayukrisna.skinsift.domain.usecase.article.ArticleUseCase
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel( private val articleUseCase: ArticleUseCase) : ViewModel() {
    private val _articleState = MutableStateFlow<Result<List<NewsResultsItem>>>(Result.Idle)
    val articleState: StateFlow<Result<List<NewsResultsItem>>> = _articleState

    fun fetchArticle() {
        _articleState.value = Result.Loading

        viewModelScope.launch {
            val result = articleUseCase.execute()
            _articleState.value = result
        }
    }
}