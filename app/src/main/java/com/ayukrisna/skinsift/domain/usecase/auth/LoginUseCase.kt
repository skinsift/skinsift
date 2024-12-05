package com.ayukrisna.skinsift.domain.usecase.auth

import com.ayukrisna.skinsift.data.remote.response.auth.LoginResult
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.UserRepository
import com.ayukrisna.skinsift.util.Result

class LoginUseCase(private val userRepository: UserRepository) {
    suspend fun execute(unameOrEmail: String, password: String): Result<Unit> {
        return try {
            val response = userRepository.login(unameOrEmail, password)
            if (response.error == false) {
                val loginResult = response.loginResult
                    ?: return Result.Error("Login result is null")
                val userModel = createUserModel(loginResult, unameOrEmail)
                userRepository.saveSession(userModel)
                Result.Success(Unit)
            } else {
                Result.Error(response.message ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    private fun createUserModel(loginResult: LoginResult, email: String): UserModel {
        return UserModel(
            username = loginResult.name ?: throw IllegalArgumentException("Name is null"),
            email = email,
            id = loginResult.userId ?: throw IllegalArgumentException("User ID is null"),
            token = loginResult.token ?: throw IllegalArgumentException("Token is null"),
            isLogin = true
        )
    }
}