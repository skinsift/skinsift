package com.ayukrisna.skinsift.domain.usecase

import com.ayukrisna.skinsift.data.remote.response.RegisterResponse
import com.ayukrisna.skinsift.domain.repository.UserRepository

class RegisterUseCase(private val userRepository: UserRepository) {
    suspend fun execute(name: String, email: String, password: String): RegisterResponse {
        return userRepository.register(name, email, password)
    }
}