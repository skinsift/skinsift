package com.ayukrisna.skinsift.view.ui.screen.profile.delete

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.auth.DeleteUserResponse
import com.ayukrisna.skinsift.domain.usecase.auth.ProfileUseCase
import com.ayukrisna.skinsift.domain.usecase.validation.ValidatePasswordUseCase
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.screen.auth.login.LoginEvent
import com.ayukrisna.skinsift.view.ui.screen.auth.login.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeleteAccountViewModel(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    var formState by mutableStateOf(LoginState())

    private val _deleteState = MutableLiveData<Result<DeleteUserResponse>>(Result.Idle)
    val deleteState: LiveData<Result<DeleteUserResponse>> = _deleteState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    fun onEvent(event: DeleteEvent) {
        when (event) {
            is DeleteEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }

            is DeleteEvent.VisiblePassword -> {
                formState = formState.copy(isVisiblePassword = event.isVisiblePassword)
            }

            is DeleteEvent.Submit -> {
                val isPasswordValid = validatePassword()

                if (isPasswordValid) {
                    deleteAccount(password = formState.password)
                }
            }
        }
    }

    private fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(formState.password, false)
        formState = formState.copy(passwordError = passwordResult.errorMessage)
        return passwordResult.successful
    }

    private fun deleteAccount(password: String){
        viewModelScope.launch {
            _deleteState.value = Result.Loading
            val result = profileUseCase.deleteAccount(password)
            _deleteState.value = result
        }
    }
}