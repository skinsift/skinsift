package com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.IngredientListItem
import com.ayukrisna.skinsift.domain.usecase.ingredient.IngredientsUseCase
import com.ayukrisna.skinsift.domain.usecase.ingredient.SearchIngredientUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.launch

class DictionaryViewModel (
    private val ingredientsUseCase: IngredientsUseCase,
    private val searchIngredientsUseCase: SearchIngredientUseCase
) : ViewModel() {
    private val _ingredientsState = MutableStateFlow<Result<List<IngredientListItem>>>(Result.Idle)
    val ingredientsState: StateFlow<Result<List<IngredientListItem>>> = _ingredientsState

    fun fetchIngredients() {
        _ingredientsState.value = Result.Loading

        viewModelScope.launch {
            val result = ingredientsUseCase.execute()
            _ingredientsState.value = result
        }
    }

    fun searchIngredients(query: String? = null, rating: List<String>?, benefit: List<String>?) {
        _ingredientsState.value = Result.Loading

        viewModelScope.launch {
            val result = searchIngredientsUseCase.execute(query = query, rating = rating, benefit = benefit)
            _ingredientsState.value = result
        }
    }
}