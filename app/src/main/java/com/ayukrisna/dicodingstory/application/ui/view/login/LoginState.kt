package com.ayukrisna.dicodingstory.application.ui.view.login

import com.ayukrisna.dicodingstory.util.UiText

data class LoginState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isVisiblePassword: Boolean = false
)