package com.ayukrisna.skinsift.domain.usecase.auth

import com.ayukrisna.skinsift.data.remote.response.auth.RegisterResponse
import com.ayukrisna.skinsift.domain.repository.UserRepository
import com.ayukrisna.skinsift.util.Result

class RegisterUseCase(private val userRepository: UserRepository) {
    suspend fun execute(username: String, email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = userRepository.register(username, email, password)
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