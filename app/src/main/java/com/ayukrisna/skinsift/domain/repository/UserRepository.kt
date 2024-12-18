package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.auth.DeleteUserResponse
import com.ayukrisna.skinsift.data.remote.response.auth.LoginResponse
import com.ayukrisna.skinsift.data.remote.response.auth.RegisterResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveSession(user: UserModel)
    fun getSession(): Flow<UserModel>
    suspend fun register(username:String, email: String, password: String): RegisterResponse
    suspend fun login(unameOrEmail: String, password: String): LoginResponse
    suspend fun logout()
    suspend fun deleteAccount(password: String) : DeleteUserResponse
}