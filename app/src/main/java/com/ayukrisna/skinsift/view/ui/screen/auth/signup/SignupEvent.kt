package com.ayukrisna.skinsift.view.ui.screen.auth.signup

sealed class SignupEvent {
    data class UsernameChanged(val username: String) : SignupEvent()
    data class EmailChanged(val email: String) : SignupEvent()
    data class PasswordChanged(val password: String) : SignupEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : SignupEvent()
    object Submit : SignupEvent()
}