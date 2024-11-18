package com.ayukrisna.dicodingstory.domain.usecase

import com.ayukrisna.dicodingstory.R
import com.ayukrisna.dicodingstory.domain.model.ValidationResult
import com.ayukrisna.dicodingstory.util.UiText

class ValidatePasswordUseCase : BaseUseCase<String, ValidationResult>() {
    override fun execute(input: String): ValidationResult {
        if (input.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.passwordLessThanEightChars),
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}