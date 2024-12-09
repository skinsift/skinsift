package com.ayukrisna.skinsift.data.repository

import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.remote.response.ml.OcrResponse
import com.ayukrisna.skinsift.data.remote.retrofit.mlapi.MLApiConfig
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.OcrRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody

class OcrRepositoryImp(private val userPreference: UserPreference): OcrRepository {
    override fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    override suspend fun submitOcr(
        file: MultipartBody.Part,
    ): OcrResponse {
        val token = userPreference.getSession().first().token
        val apiService = MLApiConfig.getApiService(token)

        val response = apiService.submitOcr(file)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    private fun parseErrorBody(errorBody: String): OcrResponse? {
        return try {
            Gson().fromJson(errorBody, OcrResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }
}