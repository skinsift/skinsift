package com.ayukrisna.skinsift.view.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.domain.usecase.LoginUseCase
import com.ayukrisna.skinsift.domain.usecase.ValidatePasswordUseCase
import com.ayukrisna.skinsift.domain.usecase.ValidateUnameOrEmailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val validateUnameOrEmailUseCase = ValidateUnameOrEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    var formState by mutableStateOf(LoginState())

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState: StateFlow<String?> = _loginState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UnameOrEmailChanged -> {
                formState = formState.copy(unameOrEmail = event.unameOrEmail)
                validateUnameOrEmail()
            }

            is LoginEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }

            is LoginEvent.VisiblePassword -> {
                formState = formState.copy(isVisiblePassword = event.isVisiblePassword)
            }

            is LoginEvent.Submit -> {
                if (validateUnameOrEmail() && validatePassword()) {
                    login(formState.unameOrEmail, formState.password )
                }
            }
        }
    }

    private fun validateUnameOrEmail(): Boolean {
        val emailResult = validateUnameOrEmailUseCase.execute(formState.unameOrEmail)
        formState = formState.copy(emailError = emailResult.errorMessage)
        return emailResult.successful
    }

    private fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(formState.password, false)
        formState = formState.copy(passwordError = passwordResult.errorMessage)
        return passwordResult.successful
    }

    private fun login(unameOrEmail: String, password: String){
        viewModelScope.launch {
            try {
                val response = loginUseCase.execute(unameOrEmail, password)
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