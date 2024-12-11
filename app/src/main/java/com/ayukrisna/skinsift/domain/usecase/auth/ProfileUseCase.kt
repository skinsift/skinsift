package com.ayukrisna.skinsift.domain.usecase.auth

import com.ayukrisna.skinsift.data.remote.response.auth.DeleteUserResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.UserRepository
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.Flow

class ProfileUseCase(private val userRepository: UserRepository) {
    fun getUserSession(): Flow<UserModel> {
        return userRepository.getSession()
    }

    suspend fun logout() {
        userRepository.logout()
    }

    suspend fun deleteAccount(password: String) : Result<DeleteUserResponse> {
        return try {
            val response = userRepository.deleteAccount(password)
            if (response.error == false) {
                userRepository.logout()
                Result.Success(response)
            } else {
                Result.Error(response.message ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}
