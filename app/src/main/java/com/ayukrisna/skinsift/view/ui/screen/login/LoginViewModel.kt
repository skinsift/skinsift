package com.ayukrisna.skinsift.view.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.domain.usecase.LoginUseCase
import com.ayukrisna.skinsift.domain.usecase.ValidateEmailUseCase
import com.ayukrisna.skinsift.domain.usecase.ValidatePasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    var formState by mutableStateOf(LoginState()) //initialize with default state values

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState: StateFlow<String?> = _loginState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

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
                    login(formState.email, formState.password )
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

    private fun login(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = loginUseCase.execute(email, password)
                _loginState.value = response.message

            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun resetStates() {
        _loginState.value = null
        _errorState.value = null
    }
}