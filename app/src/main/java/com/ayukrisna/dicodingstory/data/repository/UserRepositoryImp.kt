package com.ayukrisna.dicodingstory.data.repository

import com.ayukrisna.dicodingstory.data.local.pref.UserPreference
import com.ayukrisna.dicodingstory.data.remote.response.LoginResponse
import com.ayukrisna.dicodingstory.data.remote.response.RegisterResponse
import com.ayukrisna.dicodingstory.data.remote.retrofit.ApiConfig
import com.ayukrisna.dicodingstory.domain.model.UserModel
import com.ayukrisna.dicodingstory.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImp (
    private val userPreference: UserPreference
) : UserRepository {

    override suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    override fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    override suspend fun logout() {
        userPreference.logout()
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        val apiService = ApiConfig.getApiService()
        return apiService.loginUser(email, password)
    }

    override suspend fun register(name:String, email: String, password: String): RegisterResponse {
        val apiService = ApiConfig.getApiService()
        return apiService.registerUser(name, email, password)
    }
}