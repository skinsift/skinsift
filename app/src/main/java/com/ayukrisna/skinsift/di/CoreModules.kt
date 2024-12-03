package com.ayukrisna.skinsift.di

import com.ayukrisna.skinsift.view.ui.screen.login.LoginViewModel
import com.ayukrisna.skinsift.view.ui.screen.signup.SignupViewModel
import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.repository.UserRepositoryImp
import com.ayukrisna.skinsift.data.repository.IngredientRepositoryImp
import com.ayukrisna.skinsift.domain.repository.IngredientRepository
import com.ayukrisna.skinsift.domain.repository.UserRepository
import com.ayukrisna.skinsift.domain.usecase.IngredientsUseCase
import com.ayukrisna.skinsift.domain.usecase.LoginUseCase
import com.ayukrisna.skinsift.util.provideDataStore
import org.koin.core.module.Module
import org.koin.dsl.module
import com.ayukrisna.skinsift.domain.usecase.RegisterUseCase
import com.ayukrisna.skinsift.view.ui.screen.dictionary.DictionaryViewModel
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
    singleOf(::IngredientRepositoryImp) bind IngredientRepository::class
}

// Use Case
val useCaseModules = module {
    single { RegisterUseCase(get()) }
    single { LoginUseCase(get()) }

    single { IngredientsUseCase(get()) }
}

//View Model
val viewModelModules = module {
    viewModel{ SignupViewModel(get()) }
    viewModel{ LoginViewModel(get()) }

    viewModel{ DictionaryViewModel(get()) }
}