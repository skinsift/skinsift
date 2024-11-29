package com.ayukrisna.skinsift.view.ui.screen.login

sealed class LoginEvent {
    data class UnameOrEmailChanged(val unameOrEmail: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : LoginEvent()
    object Submit : LoginEvent()
}