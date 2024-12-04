package com.ayukrisna.skinsift.view.ui.screen.dictionary.filterdictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.Filter
import com.ayukrisna.skinsift.data.remote.response.IngredientListItem
import com.ayukrisna.skinsift.domain.usecase.ingredient.FilterIngredientUseCase
import com.ayukrisna.skinsift.domain.usecase.ingredient.IngredientsUseCase
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DictFilterViewModel(
    private val filterUseCase: FilterIngredientUseCase
) : ViewModel() {
    private val _filterState = MutableStateFlow<Result<Filter>>(Result.Idle)
    val filterState: StateFlow<Result<Filter>> = _filterState

    fun fetchFilter() {
        _filterState.value = Result.Loading

        viewModelScope.launch {
            val result = filterUseCase.execute()
            _filterState.value = result
        }
    }
}