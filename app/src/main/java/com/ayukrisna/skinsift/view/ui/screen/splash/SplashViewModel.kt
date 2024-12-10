package com.ayukrisna.skinsift.view.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.domain.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SplashViewModel (
    private val userRepository: UserRepository
) : ViewModel(){
    val userSession: StateFlow<UserModel> = userRepository.getSession()
        .map { user -> user }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserModel("", "", "", "", false))
}