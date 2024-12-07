package com.ayukrisna.skinsift.view.ui.screen.profile.delete

import com.ayukrisna.skinsift.util.UiText

data class DeleteState(
    val password: String = "",
    val passwordError: UiText? = null,
    val isVisiblePassword: Boolean = false
)