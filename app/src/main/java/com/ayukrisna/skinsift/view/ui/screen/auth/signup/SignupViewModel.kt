package com.ayukrisna.skinsift.view.ui.screen.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.RegisterResponse
import com.ayukrisna.skinsift.domain.usecase.auth.RegisterUseCase
import com.ayukrisna.skinsift.domain.usecase.validation.ValidateEmailUseCase
import com.ayukrisna.skinsift.domain.usecase.validation.ValidatePasswordUseCase
import com.ayukrisna.skinsift.domain.usecase.validation.ValidateUnameOrEmailUseCase
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {
    private val validateUsernameUseCase = ValidateUnameOrEmailUseCase()
    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    var formState by mutableStateOf(SignupState())

    private val _signUpState = MutableLiveData<Result<RegisterResponse>>(Result.Idle)
    val signUpState: LiveData<Result<RegisterResponse>> = _signUpState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.UsernameChanged -> {
                formState = formState.copy(username = event.username)
                validateUsername()
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
                val isUsernameValid = validateUsername()
                val isEmailValid = validateEmail()
                val isPasswordValid = validatePassword()

                if (isUsernameValid && isEmailValid && isPasswordValid) {
                    signUp(formState.username, formState.email, formState.password )
                }
            }
        }
    }

    private fun validateUsername(): Boolean {
        val nameResult = validateUsernameUseCase.execute(formState.username)
        formState = formState.copy(usernameError = nameResult.errorMessage)
        return nameResult.successful
    }

    private fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase.execute(formState.email)
        formState = formState.copy(emailError = emailResult.errorMessage)
        return emailResult.successful
    }

    private fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(formState.password, true)
        formState = formState.copy(passwordError = passwordResult.errorMessage)
        return passwordResult.successful
    }

    private fun signUp(username: String, email: String, password: String) {
        viewModelScope.launch {
            _signUpState.value = Result.Loading
            val result = registerUseCase.execute(username, email, password)
            _signUpState.value = result
        }
    }
}