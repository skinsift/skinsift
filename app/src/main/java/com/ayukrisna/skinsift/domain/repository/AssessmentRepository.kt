package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.ml.AssessmentResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AssessmentRepository {
    fun getSession(): Flow<UserModel>
    suspend fun submitAssessment(
        file: MultipartBody.Part,
        sensitive: String,
        reason: String,
        function: String,
        pregnantOrBreastfeeding: String
    ): AssessmentResponse
}