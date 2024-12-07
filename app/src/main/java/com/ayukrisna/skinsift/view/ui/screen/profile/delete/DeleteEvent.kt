package com.ayukrisna.skinsift.view.ui.screen.profile.delete

sealed class DeleteEvent {
    data class PasswordChanged(val password: String) : DeleteEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : DeleteEvent()
    object Submit : DeleteEvent()
}