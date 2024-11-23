package com.ayukrisna.skinsift.domain.usecase

import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.domain.model.ValidationResult
import com.ayukrisna.skinsift.util.UiText

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