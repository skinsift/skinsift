package com.ayukrisna.skinsift.data.remote.retrofit.mlapi

import com.ayukrisna.skinsift.data.remote.response.ml.AssessmentResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MLApiService {
    @Multipart
    @POST("asesmen")
    suspend fun submitAssessment(
        @Part file: MultipartBody.Part,
        @Part("sensitif") sensitif: RequestBody,
        @Part("tujuan") tujuan: RequestBody,
        @Part("fungsi") fungsi: RequestBody,
        @Part("hamil_menyusui") hamilMenyusui: RequestBody
    ) : Response<AssessmentResponse>
}