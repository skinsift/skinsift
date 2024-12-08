package com.ayukrisna.skinsift.data.remote.retrofit.mlapi

import com.ayukrisna.skinsift.data.remote.response.ml.AssessmentResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MLApiService {
    @Multipart
    @POST("asesmen")
    suspend fun submitAssessment(
        @Part file: MultipartBody.Part,
        @Part("sensitif") sensitif: String,
        @Part("tujuan") tujuan: String,
        @Part("fungsi") fungsi: List<String>,
        @Part("hamil_menyusui") hamilMenyusui: String
    ) : Response<AssessmentResponse>
}