package com.ayukrisna.dicodingstory.domain.usecase

import com.ayukrisna.dicodingstory.data.remote.response.RegisterResponse
import com.ayukrisna.dicodingstory.data.repository.UserRepository
import com.ayukrisna.dicodingstory.domain.model.UserModel

class RegisterUseCase(private val userRepository: UserRepository) {
    suspend fun execute(name: String, email: String, password: String): RegisterResponse {
        return userRepository.register(name, email, password)
    }
}