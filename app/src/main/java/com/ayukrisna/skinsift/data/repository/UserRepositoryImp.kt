package com.ayukrisna.skinsift.data.repository

import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.remote.response.LoginResponse
import com.ayukrisna.skinsift.data.remote.response.RegisterResponse
import com.ayukrisna.skinsift.data.remote.retrofit.ApiConfig
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.UserRepository
import com.google.gson.Gson
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
        val response = apiService.loginUser(unameOrEmail, password)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    override suspend fun register(username:String, email: String, password: String): RegisterResponse {
        val apiService = ApiConfig.getApiService()
        val response = apiService.registerUser(username, email, password)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    private fun parseErrorBody(errorBody: String): RegisterResponse? {
        return try {
            Gson().fromJson(errorBody, RegisterResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }
}