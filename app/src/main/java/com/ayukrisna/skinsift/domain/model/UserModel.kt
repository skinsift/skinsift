package com.ayukrisna.skinsift.domain.model

data class UserModel(
    val username: String,
    val email: String,
    val id: String,
    val token: String,
    val isLogin: Boolean = false
)