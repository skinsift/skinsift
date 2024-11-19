package com.ayukrisna.dicodingstory.data.repository

import com.ayukrisna.dicodingstory.data.local.pref.UserPreference
import com.ayukrisna.dicodingstory.data.model.LoginRequest
import com.ayukrisna.dicodingstory.data.remote.response.LoginResponse
import com.ayukrisna.dicodingstory.data.remote.response.RegisterResponse
import com.ayukrisna.dicodingstory.data.remote.retrofit.ApiConfig
import com.ayukrisna.dicodingstory.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val apiService = ApiConfig.getApiService()
        return apiService.loginUser(email, password)
    }

    suspend fun register(name:String, email: String, password: String): RegisterResponse {
        val apiService = ApiConfig.getApiService()
        return apiService.registerUser(name, email, password)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}