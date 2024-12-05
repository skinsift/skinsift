package com.ayukrisna.skinsift.view.ui.screen.product.detailproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.DetailProduct
import com.ayukrisna.skinsift.domain.usecase.product.DetailProductUseCase
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailProductViewModel (
    private val detailProductUseCase: DetailProductUseCase
    ) : ViewModel() {
        private val _productState = MutableStateFlow<Result<DetailProduct>>(Result.Idle)
        val productState: StateFlow<Result<DetailProduct>> = _productState

        fun fetchProduct(id: Int) {
            _productState.value = Result.Loading

            viewModelScope.launch {
                val result = detailProductUseCase.execute(id)
                _productState.value = result
            }
        }
    }