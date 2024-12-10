package com.ayukrisna.skinsift.data.remote.retrofit.serpApi

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SerpApiConfig {
    companion object{
        fun getSerpApiService(token: String? = null): SerpApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val authInterceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()

                val url = chain.request().url.toString()
                if (!url.contains("login") && !url.contains("signup") && !url.contains("register")) {
                    token?.let {
                        request.addHeader("Authorization", "Bearer $it")
                    }
                }

                chain.proceed(request.build())
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://serpapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(SerpApiService::class.java)
        }
    }
}