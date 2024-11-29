package com.ayukrisna.skinsift.data.repository

import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.remote.response.LoginResponse
import com.ayukrisna.skinsift.data.remote.response.RegisterResponse
import com.ayukrisna.skinsift.data.remote.retrofit.ApiConfig
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.UserRepository
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

    override suspend fun login(unameOrEmail: String, password: String): LoginResponse {
        val apiService = ApiConfig.getApiService()
        return apiService.loginUser(unameOrEmail, password)
    }

    override suspend fun register(username:String, email: String, password: String): RegisterResponse {
        val apiService = ApiConfig.getApiService()
        return apiService.registerUser(username, email, password)
    }
}