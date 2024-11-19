package com.ayukrisna.dicodingstory.di

import android.content.Context
import com.ayukrisna.dicodingstory.data.pref.UserPreference
import com.ayukrisna.dicodingstory.data.pref.dataStore
import com.ayukrisna.dicodingstory.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}