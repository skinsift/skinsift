package com.ayukrisna.skinsift.view.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.domain.usecase.LoginUseCase
import com.ayukrisna.skinsift.domain.usecase.ValidatePasswordUseCase
import com.ayukrisna.skinsift.domain.usecase.ValidateUnameOrEmailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.ayukrisna.skinsift.util.Result


class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val validateUnameOrEmailUseCase = ValidateUnameOrEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    var formState by mutableStateOf(LoginState())

    private val _loginState = MutableLiveData<Result<Unit>>(Result.Idle)
    val loginState: LiveData<Result<Unit>> = _loginState

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
                val isUnameOrEmailValid = validateUnameOrEmail()
                val isPasswordValid = validatePassword()

                if (isUnameOrEmailValid && isPasswordValid) {
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

    private fun login(usernameOrEmail: String, password: String){
        viewModelScope.launch {
            _loginState.value = Result.Loading
            val result = loginUseCase.execute(usernameOrEmail, password)
            _loginState.value = result
        }
    }
}