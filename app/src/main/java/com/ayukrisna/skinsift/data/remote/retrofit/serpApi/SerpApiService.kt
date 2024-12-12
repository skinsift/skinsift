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
        @Query("api_key") apiKey: String = "11ab9e3be390769a83a900f98c62206f1a278e941b0b0596ceff855a2962ee77"
    ): Response<SerpResponse>
}