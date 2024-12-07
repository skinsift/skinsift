package com.ayukrisna.skinsift.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val Username: String,
    val Password: String,
    val Email: String,
)

data class LoginRequest(
    val username_or_email: String,
    val password: String
)

data class DeleteUserRequest(
    @SerializedName("password")
    val password: String
)



data class SearchIngredientRequest(
    val nama: String? = null,
    val rating: List<String>? = null,
    val benefitidn: List<String>? = null
)

data class SearchProductRequest(
    val nama_atau_merk: String? = null,
    val kategori: List<String>? = null,
    val jenis_kulit: List<String>? = null,
)

data class AddNoteRequest(
    val Id_Ingredients: Int,
    val preference: String
)

data class DeleteNoteRequest(
    @SerializedName("Id_Ingredients")
    val idIngredients: Int
)