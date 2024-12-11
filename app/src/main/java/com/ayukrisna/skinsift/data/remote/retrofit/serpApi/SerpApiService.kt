package com.ayukrisna.skinsift.data.remote.retrofit.serpApi

import com.ayukrisna.skinsift.data.remote.response.article.SerpResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SerpApiService {
    @GET("search.json")
    suspend fun getNews(
        @Query("engine") engine: String = "google",
        @Query("q") query: String = "tips+skincare",
        @Query("location") location: String = "Indonesia",
        @Query("google_domain") googleDomain: String = "google.co.id",
        @Query("gl") gl: String = "id",
        @Query("hl") hl: String = "id",
        @Query("tbs") tbs: String = "tips",
        @Query("safe") safe: String = "active",
        @Query("tbm") tbm: String = "nws",
        @Query("api_key") apiKey: String = "6234143e5082e2016d52fef5dd747573ff927f2b8e4cae69434630183ac18d16"
    ): Response<SerpResponse>
}