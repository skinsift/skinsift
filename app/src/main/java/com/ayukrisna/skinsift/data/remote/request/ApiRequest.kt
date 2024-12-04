package com.ayukrisna.skinsift.data.remote.request

data class RegisterRequest(
    val Username: String,
    val Password: String,
    val Email: String,
)

data class LoginRequest(
    val username_or_email: String,
    val password: String
)

data class SearchIngredientRequest(
    val nama: String? = null,
    val rating: List<String>? = null,
    val benefitidn: List<String>? = null
)