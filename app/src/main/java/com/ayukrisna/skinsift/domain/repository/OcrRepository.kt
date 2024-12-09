package com.ayukrisna.skinsift.domain.repository

import com.ayukrisna.skinsift.data.remote.response.ml.OcrResponse
import com.ayukrisna.skinsift.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface OcrRepository {
    fun getSession(): Flow<UserModel>
    suspend fun submitOcr( file: MultipartBody.Part ): OcrResponse
}