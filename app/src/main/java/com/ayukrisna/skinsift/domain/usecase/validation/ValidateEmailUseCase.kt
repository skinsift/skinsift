package com.ayukrisna.skinsift.domain.usecase.validation

import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.domain.model.ValidationResult
import com.ayukrisna.skinsift.util.UiText

import android.util.Patterns
import com.ayukrisna.skinsift.domain.usecase.BaseUseCase

class ValidateEmailUseCase: BaseUseCase<String, ValidationResult>() {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.emailCannotBeBlank)
            )
        }
        if (!isEmailValid(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.emailIsNotValid)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

