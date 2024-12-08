package com.ayukrisna.skinsift.domain.usecase.assessment

import android.util.Log
import com.ayukrisna.skinsift.data.remote.response.ml.AssessmentResponse
import com.ayukrisna.skinsift.domain.repository.AssessmentRepository
import okhttp3.MultipartBody
import com.ayukrisna.skinsift.util.Result

class AssessmentUseCase(private val assessmentRepository: AssessmentRepository){
    suspend fun execute(photoUri: MultipartBody.Part,
                        sensitive: String,
                        reason: String,
                        function: List<String>,
                        pregnantOrBreastfeeding: String)  : Result<AssessmentResponse>  {
        return try {
            val response = assessmentRepository.submitAssessment(photoUri, sensitive, reason, function, pregnantOrBreastfeeding)
            Log.d("Assessment Result", "Assessment Result: $response")
            if (response.error == false) {
                Result.Success(response)
            } else {
                Result.Error(response.message ?: "Unknown error occurred")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}