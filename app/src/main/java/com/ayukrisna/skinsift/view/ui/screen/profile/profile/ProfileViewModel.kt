package com.ayukrisna.skinsift.view.ui.screen.profile.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.usecase.auth.ProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileUseCase: ProfileUseCase) : ViewModel() {
    private val _userData = MutableStateFlow<UserModel?>(null)
    val userData: StateFlow<UserModel?> = _userData.asStateFlow()

    init {
        viewModelScope.launch {
            profileUseCase.getUserSession()
                .catch { exception ->
                    Log.e("ProfileViewModel", "User session cannot be load: $exception")
                }
                .collect { user ->
                    _userData.value = user
                    Log.d("ProfileViewModel", "User value: ${_userData.value}")
                }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            profileUseCase.logout()
        }
    }

    fun deleteAccount(password: String) {
        viewModelScope.launch {
            profileUseCase.deleteAccount(password)
        }
    }
}
