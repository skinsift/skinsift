package com.ayukrisna.dicodingstory.view.ui.screen.login

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : LoginEvent()
    object Submit : LoginEvent()
}