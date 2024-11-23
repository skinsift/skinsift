package com.ayukrisna.dicodingstory.di

import com.ayukrisna.dicodingstory.view.ui.screen.login.LoginViewModel
import com.ayukrisna.dicodingstory.view.ui.screen.signup.SignupViewModel
import com.ayukrisna.dicodingstory.data.local.pref.UserPreference
import com.ayukrisna.dicodingstory.data.repository.UserRepositoryImp
import com.ayukrisna.dicodingstory.domain.repository.UserRepository
import com.ayukrisna.dicodingstory.domain.usecase.LoginUseCase
import com.ayukrisna.dicodingstory.util.provideDataStore
import org.koin.core.module.Module
import org.koin.dsl.module
import com.ayukrisna.dicodingstory.domain.usecase.RegisterUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind


// Data Store Module
val dataStoreModule = module {
    single { provideDataStore(get()) }
}

// Preference
val preferenceModule = module {
    single { UserPreference(get()) }
}

// Repository
var repositoryModules : Module = module {
    singleOf(::UserRepositoryImp) bind UserRepository::class
}

// Use Case
val useCaseModules = module {
    single { RegisterUseCase(get()) }
    single { LoginUseCase(get()) }
}

//View Model
val viewModelModules = module {
    viewModel{ SignupViewModel(get()) }
    viewModel{ LoginViewModel(get()) }
}