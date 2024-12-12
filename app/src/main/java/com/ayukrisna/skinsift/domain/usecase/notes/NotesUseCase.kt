package com.ayukrisna.skinsift.domain.usecase.notes

import com.ayukrisna.skinsift.data.remote.response.notes.Note
import com.ayukrisna.skinsift.domain.repository.NotesRepository
import com.ayukrisna.skinsift.util.Result

class NotesUseCase(private val notesRepository: NotesRepository) {
    suspend fun execute(): Result<List<Note?>> {
        return try {
            val response = notesRepository.getNotes()
            if (response.error == false) {
                val getProductResults = response.list
                if (getProductResults != null) {
                    Result.Success(getProductResults)
                } else {
                    Result.Error("Notes is null")
                }
            } else {
                Result.Error(response.message ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}