package com.ayukrisna.skinsift.view.ui.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ayukrisna.skinsift.view.ui.screen.signup.SignupViewModel
import com.ayukrisna.skinsift.domain.usecase.RegisterUseCase

class SignupViewModelFactory(private val registerUseCase: RegisterUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignupViewModel(registerUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}