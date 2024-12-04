package com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.IngredientListItem
import com.ayukrisna.skinsift.domain.usecase.ingredient.IngredientsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.launch

class DictionaryViewModel (
    private val ingredientsUseCase: IngredientsUseCase
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
}