package com.ayukrisna.skinsift.domain.usecase

import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.domain.model.ValidationResult
import com.ayukrisna.skinsift.util.UiText

class ValidateUnameOrEmailUseCase: BaseUseCase<String, ValidationResult>() {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.emailCannotBeBlank)
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}
