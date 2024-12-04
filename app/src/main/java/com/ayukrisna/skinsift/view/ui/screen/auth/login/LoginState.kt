package com.ayukrisna.skinsift.view.ui.screen.auth.login

import com.ayukrisna.skinsift.util.UiText

data class LoginState(
    val unameOrEmail: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isVisiblePassword: Boolean = false
)