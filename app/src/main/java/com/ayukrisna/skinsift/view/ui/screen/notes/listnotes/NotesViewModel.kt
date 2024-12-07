package com.ayukrisna.skinsift.view.ui.screen.notes.listnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.ingredients.Ingredient
import com.ayukrisna.skinsift.data.remote.response.notes.DeleteNoteResponse
import com.ayukrisna.skinsift.data.remote.response.notes.Note
import com.ayukrisna.skinsift.domain.usecase.notes.DeleteNoteUseCase
import com.ayukrisna.skinsift.domain.usecase.notes.NotesUseCase
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel (private val notesUseCase: NotesUseCase, private val deleteNoteUseCase: DeleteNoteUseCase) : ViewModel() {
    private val _notesState = MutableStateFlow<Result<List<Note?>>>(Result.Idle)
    val notesState : StateFlow<Result<List<Note?>>> = _notesState

    private val _deleteNoteState = MutableStateFlow<Result<DeleteNoteResponse>>(Result.Idle)
    val deleteNoteState : StateFlow<Result<DeleteNoteResponse>> = _deleteNoteState

    fun fetchNotes() {
        _notesState.value = Result.Loading

        viewModelScope.launch {
            val result = notesUseCase.execute()
            _notesState.value = result
        }
    }

    fun deleteNote(id: Int) {
        _deleteNoteState.value = Result.Loading

        viewModelScope.launch {
            val result = deleteNoteUseCase.execute(id)
            _deleteNoteState.value = result
        }
    }
}