package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.notes.NotesResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getSession(): Flow<UserModel>
    suspend fun getNotes(): NotesResponse
}