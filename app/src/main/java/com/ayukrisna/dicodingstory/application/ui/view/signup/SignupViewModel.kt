package com.ayukrisna.dicodingstory.application.ui.view.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ayukrisna.dicodingstory.application.ui.view.signup.SignupEvent
import com.ayukrisna.dicodingstory.application.ui.view.signup.SignupState
import com.ayukrisna.dicodingstory.domain.usecase.ValidateEmailUseCase
import com.ayukrisna.dicodingstory.domain.usecase.ValidateNameUseCase
import com.ayukrisna.dicodingstory.domain.usecase.ValidatePasswordUseCase

class SignupViewModel : ViewModel() {
    private val validateNameUseCase = ValidateNameUseCase()
    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    var formState by mutableStateOf(SignupState()) //initialize with default state values

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.NameChanged -> {
                formState = formState.copy(name = event.name)
                validateName()
            }

            is SignupEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
                validateEmail()
            }

            is SignupEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
                validatePassword()
            }

            is SignupEvent.VisiblePassword -> {
                formState = formState.copy(isVisiblePassword = event.isVisiblePassword)
            }

            is SignupEvent.Submit -> {
                if (validateName() && validateEmail() && validatePassword()) {
                    //go to next page
                }
            }
        }
    }

    private fun validateName(): Boolean {
        val nameResult = validateNameUseCase.execute(formState.name)
        formState = formState.copy(nameError = nameResult.errorMessage)
        return nameResult.successful
    }

    private fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase.execute(formState.email)
        formState = formState.copy(emailError = emailResult.errorMessage)
        return emailResult.successful
    }

    private fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(formState.password)
        formState = formState.copy(passwordError = passwordResult.errorMessage)
        return passwordResult.successful
    }
}