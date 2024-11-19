package com.ayukrisna.dicodingstory.domain.usecase

import android.util.Patterns
import com.ayukrisna.dicodingstory.R
import com.ayukrisna.dicodingstory.domain.model.ValidationResult
import com.ayukrisna.dicodingstory.util.UiText

class ValidateNameUseCase: BaseUseCase<String, ValidationResult>() {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.nameCannotBeBlank)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}


