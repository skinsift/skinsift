package com.ayukrisna.skinsift.view.ui.screen.product.listproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.ProductListItem
import com.ayukrisna.skinsift.domain.usecase.product.ProductUseCase
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel (
    private val productUseCase: ProductUseCase
) : ViewModel() {
    private val _productsState = MutableStateFlow<Result<List<ProductListItem>>>(Result.Idle)
    val productsState: StateFlow<Result<List<ProductListItem>>> = _productsState

    fun fetchProducts() {
        _productsState.value = Result.Loading

        viewModelScope.launch {
            val result = productUseCase.execute()
            _productsState.value = result
        }
    }
}