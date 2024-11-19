package com.ayukrisna.dicodingstory.di

import android.content.Context
import com.ayukrisna.dicodingstory.data.local.pref.UserPreference
import com.ayukrisna.dicodingstory.data.local.pref.dataStore
import com.ayukrisna.dicodingstory.data.repository.UserRepository
import com.ayukrisna.dicodingstory.domain.usecase.RegisterUseCase

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun provideRegisterUseCase(context: Context): RegisterUseCase {
        val repository = provideRepository(context)
        return RegisterUseCase(repository)
    }
}