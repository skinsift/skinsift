package com.ayukrisna.skinsift.domain.usecase.notes

import com.ayukrisna.skinsift.data.remote.response.notes.AddNoteResponse
import com.ayukrisna.skinsift.domain.repository.NotesRepository
import com.ayukrisna.skinsift.util.Result

class AddNoteUseCase(private val notesRepository: NotesRepository) {
    suspend fun execute(idIngredient: Int, preference: String): Result<AddNoteResponse> {
        return try {
            val response = notesRepository.addNote(idIngredient, preference)
            if (response.error == false) {
                Result.Success(response)
            } else {
                Result.Error(response.message ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}