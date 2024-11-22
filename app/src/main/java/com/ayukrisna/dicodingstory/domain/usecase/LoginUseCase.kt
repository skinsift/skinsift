package com.ayukrisna.dicodingstory.domain.usecase

import com.ayukrisna.dicodingstory.data.remote.response.LoginResponse
import com.ayukrisna.dicodingstory.data.remote.response.LoginResult
import com.ayukrisna.dicodingstory.domain.model.UserModel
import com.ayukrisna.dicodingstory.domain.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {
    suspend fun execute(email: String, password: String): LoginResponse {
        val response = userRepository.login(email, password)
        if (!response.error!!) {
            val loginResult = response.loginResult!!
            val userModel = createUserModel(loginResult, email)
            userRepository.saveSession(userModel)
        }
        return response
    }

    private fun createUserModel(loginResult: LoginResult, email: String): UserModel {
        return UserModel(
            name = loginResult.name ?: throw IllegalArgumentException("Name is null"),
            email = email,
            id = loginResult.userId ?: throw IllegalArgumentException("User ID is null"),
            token = loginResult.token ?: throw IllegalArgumentException("Token is null"),
            isLogin = true
        )
    }
}