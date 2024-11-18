package com.ayukrisna.dicodingstory.domain.usecase

import android.util.Patterns
import com.ayukrisna.dicodingstory.R
import com.ayukrisna.dicodingstory.domain.model.ValidationResult
import com.ayukrisna.dicodingstory.util.UiText

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

