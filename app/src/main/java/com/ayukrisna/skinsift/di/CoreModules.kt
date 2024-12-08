package com.ayukrisna.skinsift.di

import com.ayukrisna.skinsift.view.ui.screen.auth.login.LoginViewModel
import com.ayukrisna.skinsift.view.ui.screen.auth.signup.SignupViewModel
import com.ayukrisna.skinsift.data.local.pref.UserPreference
import com.ayukrisna.skinsift.data.repository.UserRepositoryImp
import com.ayukrisna.skinsift.data.repository.IngredientRepositoryImp
import com.ayukrisna.skinsift.data.repository.ProductRepositoryImp
import com.ayukrisna.skinsift.data.repository.NotesRepositoryImp
import com.ayukrisna.skinsift.data.repository.AssessmentRepositoryImp
import com.ayukrisna.skinsift.domain.repository.AssessmentRepository
import com.ayukrisna.skinsift.domain.repository.IngredientRepository
import com.ayukrisna.skinsift.domain.repository.NotesRepository
import com.ayukrisna.skinsift.domain.repository.ProductRepository
import com.ayukrisna.skinsift.domain.repository.UserRepository
import com.ayukrisna.skinsift.domain.usecase.assessment.AssessmentUseCase
import com.ayukrisna.skinsift.domain.usecase.ingredient.IngredientsUseCase
import com.ayukrisna.skinsift.domain.usecase.auth.LoginUseCase
import com.ayukrisna.skinsift.domain.usecase.auth.ProfileUseCase
import com.ayukrisna.skinsift.util.provideDataStore
import org.koin.core.module.Module
import org.koin.dsl.module
import com.ayukrisna.skinsift.domain.usecase.auth.RegisterUseCase
import com.ayukrisna.skinsift.domain.usecase.ingredient.DetailIngredientUseCase
import com.ayukrisna.skinsift.domain.usecase.ingredient.FilterIngredientUseCase
import com.ayukrisna.skinsift.domain.usecase.ingredient.SearchIngredientUseCase
import com.ayukrisna.skinsift.domain.usecase.notes.AddNoteUseCase
import com.ayukrisna.skinsift.domain.usecase.notes.DeleteNoteUseCase
import com.ayukrisna.skinsift.domain.usecase.notes.NotesUseCase
import com.ayukrisna.skinsift.domain.usecase.product.DetailProductUseCase
import com.ayukrisna.skinsift.domain.usecase.product.FilterProductUseCase
import com.ayukrisna.skinsift.domain.usecase.product.ProductUseCase
import com.ayukrisna.skinsift.domain.usecase.product.SearchProductUseCase
import com.ayukrisna.skinsift.util.FileHelper
import com.ayukrisna.skinsift.view.ui.screen.assessment.AssessmentViewModel
import com.ayukrisna.skinsift.view.ui.screen.dictionary.detaildictionary.DictDetailViewModel
import com.ayukrisna.skinsift.view.ui.screen.dictionary.filterdictionary.DictFilterViewModel
import com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary.DictionaryViewModel
import com.ayukrisna.skinsift.view.ui.screen.notes.addnotes.AddNoteViewModel
import com.ayukrisna.skinsift.view.ui.screen.notes.listnotes.NotesViewModel
import com.ayukrisna.skinsift.view.ui.screen.notes.searchnotes.SearchNoteViewModel
import com.ayukrisna.skinsift.view.ui.screen.product.detailproduct.DetailProductViewModel
import com.ayukrisna.skinsift.view.ui.screen.product.filterproduct.ProductFilterViewModel
import com.ayukrisna.skinsift.view.ui.screen.product.listproduct.ProductViewModel
import com.ayukrisna.skinsift.view.ui.screen.profile.delete.DeleteAccountViewModel
import com.ayukrisna.skinsift.view.ui.screen.profile.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind

// File Helper Module
val fileHelperModule = module {
    single { FileHelper(androidContext()) }
}

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
    singleOf(::ProductRepositoryImp) bind ProductRepository::class
    singleOf(::NotesRepositoryImp) bind NotesRepository::class
    singleOf(::AssessmentRepositoryImp) bind AssessmentRepository::class
}

// Use Case
val useCaseModules = module {
    single { RegisterUseCase(get()) }
    single { LoginUseCase(get()) }
    single { ProfileUseCase(get()) }

    single { IngredientsUseCase(get()) }
    single { DetailIngredientUseCase(get()) }
    single { FilterIngredientUseCase(get()) }
    single { SearchIngredientUseCase(get()) }

    single { ProductUseCase(get()) }
    single { DetailProductUseCase(get()) }
    single { FilterProductUseCase(get()) }
    single { SearchProductUseCase(get()) }

    single { NotesUseCase(get()) }
    single { AddNoteUseCase(get()) }
    single { DeleteNoteUseCase(get()) }

    single { AssessmentUseCase(get()) }
}

//View Model
val viewModelModules = module {
    viewModel{ SignupViewModel(get()) }
    viewModel{ LoginViewModel(get()) }
    viewModel{ DeleteAccountViewModel(get()) }
    viewModel{ ProfileViewModel(get()) }

    viewModel{ DictFilterViewModel(get()) }
    viewModel{ DictionaryViewModel(get(), get()) }
    viewModel{ DictDetailViewModel(get()) }

    viewModel{ ProductFilterViewModel(get()) }
    viewModel{ ProductViewModel(get(), get()) }
    viewModel{ DetailProductViewModel(get()) }

    viewModel{ NotesViewModel(get(), get()) }
    viewModel{ SearchNoteViewModel() }
    viewModel{ AddNoteViewModel(get()) }

    viewModel{ AssessmentViewModel(get(), get()) }
}