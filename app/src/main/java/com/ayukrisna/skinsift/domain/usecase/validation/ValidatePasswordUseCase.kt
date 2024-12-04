package com.ayukrisna.skinsift.domain.usecase.validation

import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.domain.model.ValidationResult
import com.ayukrisna.skinsift.util.UiText

class ValidatePasswordUseCase {
    fun execute(input: String, checkLength: Boolean): ValidationResult {
        if (checkLength && input.length < 7) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.passwordLessThanSevenChars),
            )
        }

        val containsLettersAndNumbers = input.any { it.isLetter() } && input.any { it.isDigit() }
        if (checkLength && !containsLettersAndNumbers) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.passwordMustContainLettersAndNumbers),
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}