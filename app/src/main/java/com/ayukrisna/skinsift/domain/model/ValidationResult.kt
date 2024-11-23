package com.ayukrisna.skinsift.domain.model

import com.ayukrisna.skinsift.util.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)