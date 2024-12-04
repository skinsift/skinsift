package com.ayukrisna.skinsift.data.remote.retrofit

import com.ayukrisna.skinsift.data.remote.response.DetailIngredientsResponse
import com.ayukrisna.skinsift.data.remote.response.FilterIngreResponse
import com.ayukrisna.skinsift.data.remote.response.IngredientsResponse
import com.ayukrisna.skinsift.data.remote.response.LoginResponse
import com.ayukrisna.skinsift.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<LoginResponse>

    @GET("ingredient")
    suspend fun getIngredients(): Response<IngredientsResponse>

    @GET("ingredient/{id}")
    suspend fun getDetailIngredients(
        @Path("id") id: Int
    ): Response<DetailIngredientsResponse>

    @GET("ingredient/filter")
    suspend fun getFilterIngredient(): Response<FilterIngreResponse>
}