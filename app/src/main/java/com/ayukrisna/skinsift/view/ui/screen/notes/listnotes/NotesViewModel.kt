package com.ayukrisna.skinsift.view.ui.screen.notes.listnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.notes.Note
import com.ayukrisna.skinsift.domain.usecase.notes.NotesUseCase
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel (private val notesUseCase: NotesUseCase) : ViewModel() {
    private val _notesState = MutableStateFlow<Result<List<Note?>>>(Result.Idle)
    val notesState : StateFlow<Result<List<Note?>>> = _notesState

    fun fetchNotes() {
        _notesState.value = Result.Loading

        viewModelScope.launch {
            val result = notesUseCase.execute()
            _notesState.value = result
        }
    }
}