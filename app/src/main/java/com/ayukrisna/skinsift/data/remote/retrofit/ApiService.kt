package com.ayukrisna.skinsift.data.remote.retrofit

import com.ayukrisna.skinsift.data.remote.request.AddNoteRequest
import com.ayukrisna.skinsift.data.remote.request.DeleteNoteRequest
import com.ayukrisna.skinsift.data.remote.request.DeleteUserRequest
import com.ayukrisna.skinsift.data.remote.request.LoginRequest
import com.ayukrisna.skinsift.data.remote.request.RegisterRequest
import com.ayukrisna.skinsift.data.remote.request.SearchIngredientRequest
import com.ayukrisna.skinsift.data.remote.request.SearchProductRequest
import com.ayukrisna.skinsift.data.remote.response.auth.DeleteUserResponse
import com.ayukrisna.skinsift.data.remote.response.ingredients.DetailIngredientsResponse
import com.ayukrisna.skinsift.data.remote.response.product.DetailProductResponse
import com.ayukrisna.skinsift.data.remote.response.ingredients.FilterIngreResponse
import com.ayukrisna.skinsift.data.remote.response.product.FilterProductResponse
import com.ayukrisna.skinsift.data.remote.response.ingredients.IngredientsResponse
import com.ayukrisna.skinsift.data.remote.response.auth.LoginResponse
import com.ayukrisna.skinsift.data.remote.response.product.ProductResponse
import com.ayukrisna.skinsift.data.remote.response.auth.RegisterResponse
import com.ayukrisna.skinsift.data.remote.response.notes.AddNoteResponse
import com.ayukrisna.skinsift.data.remote.response.notes.DeleteNoteResponse
import com.ayukrisna.skinsift.data.remote.response.notes.NotesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
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

    @HTTP(method = "DELETE", path = "delete-account", hasBody = true)
    suspend fun deleteUser(
        @Body request: DeleteUserRequest
    ) : Response<DeleteUserResponse>

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

    @GET("product/filter")
    suspend fun getFilterProduct(): Response<FilterProductResponse>

    @POST("product/search")
    suspend fun searchProduct(
        @Body request: SearchProductRequest
    ) : Response<ProductResponse>

    @GET("user/notes")
    suspend fun getNotes() : Response<NotesResponse>

    @POST("user/notes")
    suspend fun addNote(
        @Body request: AddNoteRequest
    ) : Response<AddNoteResponse>

    @HTTP(method = "DELETE", path = "user/notes", hasBody = true)
    suspend fun deleteNote(
        @Body request: DeleteNoteRequest
    ) : Response<DeleteNoteResponse>
}