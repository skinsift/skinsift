package com.ayukrisna.skinsift.view.ui.screen.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.Ingredient
import com.ayukrisna.skinsift.domain.usecase.DetailIngredientUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DictDetailViewModel (
    private val detailIngredientUseCase: DetailIngredientUseCase
) : ViewModel() {
    private val _ingredientState = MutableStateFlow<Result<Ingredient>>(Result.Idle)
    val ingredientState: StateFlow<Result<Ingredient>> = _ingredientState

    fun fetchIngredient(id: Int) {
        _ingredientState.value = Result.Loading

        viewModelScope.launch {
            val result = detailIngredientUseCase.execute(id)
            _ingredientState.value = result
        }
    }
}