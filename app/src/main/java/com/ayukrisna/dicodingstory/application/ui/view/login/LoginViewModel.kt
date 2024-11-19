package com.ayukrisna.dicodingstory.application.ui.view.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ayukrisna.dicodingstory.domain.usecase.ValidateEmailUseCase
import com.ayukrisna.dicodingstory.domain.usecase.ValidatePasswordUseCase

class LoginViewModel : ViewModel() {
    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    var formState by mutableStateOf(LoginState()) //initialize with default state values

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
                validateEmail()
            }

            is LoginEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
                validatePassword()
            }

            is LoginEvent.VisiblePassword -> {
                formState = formState.copy(isVisiblePassword = event.isVisiblePassword)
            }

            is LoginEvent.Submit -> {
                if (validateEmail() && validatePassword()) {
                    //go to next page
                }
            }
        }
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