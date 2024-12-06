package com.ayukrisna.skinsift.view.ui.screen.notes.searchnotes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.ingredients.IngredientListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchNoteViewModel(): ViewModel() {
    private val _selectedIngredient = MutableStateFlow<IngredientListItem?>(null)
    val selectedIngredient: StateFlow<IngredientListItem?> = _selectedIngredient.asStateFlow()

    fun selectIngredient(ingredient: IngredientListItem) {
        _selectedIngredient.value = ingredient
        Log.d("SeachViewModel", "Selected ingredient: ${_selectedIngredient.value}")
    }
}