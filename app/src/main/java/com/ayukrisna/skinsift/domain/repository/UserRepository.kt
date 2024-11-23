package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.LoginResponse
import com.ayukrisna.skinsift.data.remote.response.RegisterResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveSession(user: UserModel)
    fun getSession(): Flow<UserModel>
    suspend fun register(name:String, email: String, password: String): RegisterResponse
    suspend fun login(email: String, password: String): LoginResponse
    suspend fun logout()
}