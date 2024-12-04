package com.ayukrisna.skinsift.view.ui.screen.auth.signup

import com.ayukrisna.skinsift.util.UiText

data class SignupState(
    val username: String = "",
    val usernameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isVisiblePassword: Boolean = false
)