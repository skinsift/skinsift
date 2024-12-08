package com.ayukrisna.skinsift.data.repository

import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.remote.response.ml.AssessmentResponse
import com.ayukrisna.skinsift.data.remote.retrofit.mlapi.MLApiConfig
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.AssessmentRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class AssessmentRepositoryImp (private val userPreference: UserPreference) : AssessmentRepository{
    override fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    override suspend fun submitAssessment(
        photoUri: MultipartBody.Part,
        sensitive: String,
        reason: String,
        function: String,
        pregnantOrBreastfeeding: String
    ): AssessmentResponse {
        val token = userPreference.getSession().first().token
        val apiService = MLApiConfig.getApiService(token)

        val sensitiveBody = sensitive.toRequestBody("text/plain".toMediaTypeOrNull())
        val reasonBody = sensitive.toRequestBody("text/plain".toMediaTypeOrNull())
        val functionBody = sensitive.toRequestBody("text/plain".toMediaTypeOrNull())
        val pregnantOrBreastfeedingBody = sensitive.toRequestBody("text/plain".toMediaTypeOrNull())

        val response = apiService.submitAssessment(photoUri, sensitiveBody, reasonBody, functionBody, pregnantOrBreastfeedingBody)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response body is null")
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorBody(it) }
            throw Exception(errorResponse?.message ?: "HTTP ${response.code()} error")
        }
    }

    private fun parseErrorBody(errorBody: String): AssessmentResponse? {
        return try {
            Gson().fromJson(errorBody, AssessmentResponse::class.java)
        } catch (e: Exception) {
            null
        }
    }
}