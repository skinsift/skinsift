package com.ayukrisna.dicodingstory.view.ui.screen.login

import com.ayukrisna.dicodingstory.util.UiText

data class LoginState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isVisiblePassword: Boolean = false
)