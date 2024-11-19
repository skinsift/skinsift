package com.ayukrisna.dicodingstory.application.ui.view.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.dicodingstory.domain.usecase.RegisterUseCase
import com.ayukrisna.dicodingstory.domain.usecase.ValidateEmailUseCase
import com.ayukrisna.dicodingstory.domain.usecase.ValidateNameUseCase
import com.ayukrisna.dicodingstory.domain.usecase.ValidatePasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {
    private val validateNameUseCase = ValidateNameUseCase()
    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    var formState by mutableStateOf(SignupState()) //initialize with default state values
    private val _signUpState = MutableStateFlow<String?>(null)
    val signUpState: StateFlow<String?> = _signUpState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

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
                    signUp(formState.name, formState.email, formState.password )
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

    private fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = registerUseCase.execute(name, email, password)
                _signUpState.value = response.message
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun resetStates() {
        _signUpState.value = null
        _errorState.value = null
    }
}