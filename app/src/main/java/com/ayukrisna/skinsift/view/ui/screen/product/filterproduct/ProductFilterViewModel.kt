package com.ayukrisna.skinsift.view.ui.screen.product.filterproduct

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.product.ProductFilter
import com.ayukrisna.skinsift.domain.usecase.product.FilterProductUseCase
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductFilterViewModel(
    private val filterUseCase: FilterProductUseCase
) : ViewModel() {
    private val _filterState = MutableStateFlow<Result<ProductFilter>>(Result.Idle)
    val filterState: StateFlow<Result<ProductFilter>> = _filterState

    private val _selectedFilters = MutableStateFlow<MutableMap<String, MutableList<String>>>(mutableMapOf())
    val selectedFilters: StateFlow<Map<String, List<String>>> = _selectedFilters

    fun fetchFilter() {
        _filterState.value = Result.Loading

        viewModelScope.launch {
            val result = filterUseCase.execute()
            _filterState.value = result
        }
    }

    fun toggleFilter(category: String, option: String) {
        val newFilters = _selectedFilters.value.toMutableMap()
        val options = newFilters.getOrPut(category) { mutableListOf() }.toMutableList()

        if (options.contains(option)) {
            options.remove(option)
        } else {
            options.add(option)
        }

        newFilters[category] = options
        _selectedFilters.value = newFilters

        Log.d("DictFilterViewModel", "Category: $category, Option: $option")
        Log.d("DictFilterViewModel", "Filters: ${_selectedFilters.value}")
    }
}