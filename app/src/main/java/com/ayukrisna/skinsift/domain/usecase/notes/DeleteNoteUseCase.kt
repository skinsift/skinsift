package com.ayukrisna.skinsift.domain.usecase.notes

import com.ayukrisna.skinsift.data.remote.response.notes.DeleteNoteResponse
import com.ayukrisna.skinsift.domain.repository.NotesRepository
import com.ayukrisna.skinsift.util.Result

class DeleteNoteUseCase(private val notesRepository: NotesRepository) {
    suspend fun execute(idIngredient: Int): Result<DeleteNoteResponse> {
        return try {
            val response = notesRepository.deleteNote(idIngredient)
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