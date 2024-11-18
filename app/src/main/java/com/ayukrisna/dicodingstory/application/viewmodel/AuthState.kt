package com.ayukrisna.dicodingstory.application.viewmodel

import com.ayukrisna.dicodingstory.util.UiText

data class AuthState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isVisiblePassword: Boolean = false
)