package com.ayukrisna.skinsift.data.repository

import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.remote.request.AddNoteRequest
import com.ayukrisna.skinsift.data.remote.request.DeleteNoteRequest
import com.ayukrisna.skinsift.data.remote.response.notes.AddNoteResponse
import com.ayukrisna.skinsift.data.remote.response.notes.DeleteNoteResponse
import com.ayukrisna.skinsift.data.remote.response.notes.NotesResponse
import com.ayukrisna.skinsift.data.remote.retrofit.api.ApiConfig
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.NotesRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NotesRepositoryImp(private val userPreference: UserPreference) : NotesRepository {
    override fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    override suspend fun getNotes(): NotesResponse {
        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.getNotes()

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    override suspend fun addNote(idIngredient: Int, preference: String): AddNoteResponse {
        val newNote = AddNoteRequest(idIngredient, preference)
        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.addNote(newNote)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    override suspend fun deleteNote(idIngredient: Int): DeleteNoteResponse {
        val deletedId = DeleteNoteRequest(idIngredient)
        val token = userPreference.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        val response = apiService.deleteNote(deletedId)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    private fun parseErrorBody(errorBody: String): NotesResponse? {
        return try {
            Gson().fromJson(errorBody, NotesResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }
}