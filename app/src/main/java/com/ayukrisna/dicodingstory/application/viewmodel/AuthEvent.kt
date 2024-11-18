package com.ayukrisna.dicodingstory.application.viewmodel

sealed class AuthEvent {
    data class EmailChanged(val email: String) : AuthEvent()
    data class PasswordChanged(val password: String) : AuthEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : AuthEvent()
    object Submit : AuthEvent()
}