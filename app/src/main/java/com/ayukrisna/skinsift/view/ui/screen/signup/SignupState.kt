package com.ayukrisna.skinsift.view.ui.screen.signup

import com.ayukrisna.skinsift.util.UiText

data class SignupState(
    val name: String = "",
    val nameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val isVisiblePassword: Boolean = false
)