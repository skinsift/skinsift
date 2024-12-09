package com.ayukrisna.skinsift.domain.usecase.ocr

import android.util.Log
import com.ayukrisna.skinsift.data.remote.response.ml.OcrResponse
import com.ayukrisna.skinsift.domain.repository.OcrRepository
import com.ayukrisna.skinsift.util.Result
import okhttp3.MultipartBody

class OcrUseCase(private val ocrRepository: OcrRepository){
    suspend fun execute(photoUri: MultipartBody.Part)  : Result<OcrResponse> {
        return try {
            val response = ocrRepository.submitOcr(photoUri)
            Log.d("Ocr Result", "Ocr Result: $response")
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