package com.ayukrisna.dicodingstory.domain.model

import com.ayukrisna.dicodingstory.util.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)