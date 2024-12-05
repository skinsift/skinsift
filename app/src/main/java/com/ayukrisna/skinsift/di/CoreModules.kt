package com.ayukrisna.skinsift.di

import com.ayukrisna.skinsift.view.ui.screen.auth.login.LoginViewModel
import com.ayukrisna.skinsift.view.ui.screen.auth.signup.SignupViewModel
import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.repository.UserRepositoryImp
import com.ayukrisna.skinsift.data.repository.IngredientRepositoryImp
import com.ayukrisna.skinsift.domain.repository.IngredientRepository
import com.ayukrisna.skinsift.domain.repository.UserRepository
import com.ayukrisna.skinsift.domain.usecase.ingredient.IngredientsUseCase
import com.ayukrisna.skinsift.domain.usecase.auth.LoginUseCase
import com.ayukrisna.skinsift.util.provideDataStore
import org.koin.core.module.Module
import org.koin.dsl.module
import com.ayukrisna.skinsift.domain.usecase.auth.RegisterUseCase
import com.ayukrisna.skinsift.domain.usecase.ingredient.DetailIngredientUseCase
import com.ayukrisna.skinsift.domain.usecase.ingredient.FilterIngredientUseCase
import com.ayukrisna.skinsift.domain.usecase.ingredient.SearchIngredientUseCase
import com.ayukrisna.skinsift.view.ui.screen.dictionary.detaildictionary.DictDetailViewModel
import com.ayukrisna.skinsift.view.ui.screen.dictionary.filterdictionary.DictFilterViewModel
import com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary.DictionaryViewModel
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
    single { DetailIngredientUseCase(get()) }
    single { FilterIngredientUseCase(get()) }
    single { SearchIngredientUseCase(get()) }
}

//View Model
val viewModelModules = module {
    viewModel{ SignupViewModel(get()) }
    viewModel{ LoginViewModel(get()) }

    viewModel{ DictFilterViewModel(get()) }
    viewModel{ DictionaryViewModel(get(), get()) }
    viewModel{ DictDetailViewModel(get()) }
}