package com.ayukrisna.skinsift.data.remote.retrofit

import com.ayukrisna.skinsift.data.remote.request.LoginRequest
import com.ayukrisna.skinsift.data.remote.request.RegisterRequest
import com.ayukrisna.skinsift.data.remote.request.SearchIngredientRequest
import com.ayukrisna.skinsift.data.remote.response.DetailIngredientsResponse
import com.ayukrisna.skinsift.data.remote.response.DetailProductResponse
import com.ayukrisna.skinsift.data.remote.response.FilterIngreResponse
import com.ayukrisna.skinsift.data.remote.response.IngredientsResponse
import com.ayukrisna.skinsift.data.remote.response.LoginResponse
import com.ayukrisna.skinsift.data.remote.response.ProductResponse
import com.ayukrisna.skinsift.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("register")
    suspend fun registerUser(
        @Body request: RegisterRequest
    ) : Response<RegisterResponse>

    @POST("login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ) : Response<LoginResponse>

    @GET("ingredient")
    suspend fun getIngredients(): Response<IngredientsResponse>

    @GET("ingredient/detail/{id}")
    suspend fun getDetailIngredients(
        @Path("id") id: Int
    ): Response<DetailIngredientsResponse>

    @GET("ingredient/filter")
    suspend fun getFilterIngredient(): Response<FilterIngreResponse>

    @POST("ingredient/search")
    suspend fun searchIngredient(
        @Body request: SearchIngredientRequest
    ) : Response<IngredientsResponse>

    @GET("product")
    suspend fun getProducts(): Response<ProductResponse>

    @GET("product/detail/{id}")
    suspend fun getDetailProduct(
        @Path("id") id: Int
    ): Response<DetailProductResponse>
}