package com.ayukrisna.skinsift.view.ui.screen.notes.addnotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.notes.AddNoteResponse
import com.ayukrisna.skinsift.domain.usecase.notes.AddNoteUseCase
import kotlinx.coroutines.launch
import com.ayukrisna.skinsift.util.Result

class AddNoteViewModel(private val addNoteUseCase: AddNoteUseCase) : ViewModel() {
    private val _addNoteState = MutableLiveData<Result<AddNoteResponse>>(Result.Idle)
    val addNoteState: LiveData<Result<AddNoteResponse>> = _addNoteState

    fun addNote(idIngredient: Int, preference: String) {
        viewModelScope.launch {
            _addNoteState.value = Result.Loading
            val result = addNoteUseCase.execute(
                idIngredient = idIngredient,
                preference = preference
            )
            _addNoteState.value = result
        }
    }
}