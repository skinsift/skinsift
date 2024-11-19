package com.ayukrisna.dicodingstory.domain.model

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)