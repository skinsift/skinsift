package com.ayukrisna.skinsift.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.ayukrisna.skinsift.view.ui.screen.assessment.AllergyAssessmentScreen
import com.ayukrisna.skinsift.view.ui.screen.assessment.AssessmentResultScreen
import com.ayukrisna.skinsift.view.ui.screen.assessment.FunctionAssessmentScreen
import com.ayukrisna.skinsift.view.ui.screen.assessment.PurposeAssessmentScreen
import com.ayukrisna.skinsift.view.ui.screen.assessment.SkinAssessmentScreen
import com.ayukrisna.skinsift.view.ui.screen.assessment.StartAssessmentScreen
import com.ayukrisna.skinsift.view.ui.screen.dictionary.detaildictionary.DictDetailScreen
import com.ayukrisna.skinsift.view.ui.screen.dictionary.filterdictionary.DictFilterScreen
import com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary.DictionaryScreen
import com.ayukrisna.skinsift.view.ui.screen.home.HomeScreen
import com.ayukrisna.skinsift.view.ui.screen.auth.login.LoginScreen
import com.ayukrisna.skinsift.view.ui.screen.notes.addnotes.AddNoteScreen
import com.ayukrisna.skinsift.view.ui.screen.product.detailproduct.ProductDetailScreen
import com.ayukrisna.skinsift.view.ui.screen.profile.profile.ProfileScreen
import com.ayukrisna.skinsift.view.ui.screen.auth.signup.SignupScreen
import com.ayukrisna.skinsift.view.ui.screen.home.Profile
import com.ayukrisna.skinsift.view.ui.screen.notes.listnotes.NotesScreen
import com.ayukrisna.skinsift.view.ui.screen.notes.searchnotes.SearchNoteScreen
import com.ayukrisna.skinsift.view.ui.screen.product.filterproduct.ProductFilterScreen
import com.ayukrisna.skinsift.view.ui.screen.product.listproduct.ProductScreen
import com.ayukrisna.skinsift.view.ui.screen.profile.delete.DeleteAccountScreen

@Composable
fun NavGraph (
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController,
        startDestination = RootScreen.AuthNav,
        enterTransition = { fadeIn(tween(100))},
        popEnterTransition = {EnterTransition.None},
        exitTransition = { fadeOut(tween(100))},
        popExitTransition = {ExitTransition.None}
    ) {
        authNavGraph(navController, paddingValues)
        homeNavGraph(navController, paddingValues)
        notesNavGraph(navController, paddingValues)
        assessNavGraph(navController, paddingValues)
        dictionaryNavGraph(navController, paddingValues)
        productNavGraph(navController, paddingValues)
        profileNavGraph(navController, paddingValues)
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation<RootScreen.AuthNav>(
        startDestination = AuthScreen.Login
    ) {
        composable<AuthScreen.Login> {
            LoginScreen(
                onNavigateToSignup = {
                    navController.navigate(AuthScreen.Signup)
                },
                onNavigateToHome = {
                    navController.navigate(HomeScreen.Home) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
//                        launchSingleTop
                    }
                },
                paddingValues = paddingValues
            )
        }
        composable<AuthScreen.Signup> {
            SignupScreen(
                onNavigateToLogin = {
                    navController.navigate(AuthScreen.Login)
                },
                paddingValues = paddingValues
            )
        }
    }
}

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation<RootScreen.HomeNav>(
        startDestination = HomeScreen.Home
    ) {
        composable<HomeScreen.Home> {
            HomeScreen(
                paddingValues = paddingValues,
                onNavigateToNotes = {
                    navController.navigate(NotesScreen.Notes)
                },
                onNavigateToAssessment = {
                    navController.navigate(AssessmentScreen.Start)
                },
            )
        }
    }
}

fun NavGraphBuilder.notesNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation<RootScreen.NotesNav>(
        startDestination = NotesScreen.Notes
    ) {
        composable<NotesScreen.Notes> {
            NotesScreen(
                paddingValues = paddingValues,
                onNavigateToDetail = { id ->
                    navController.navigate(DictionaryScreen.Detail(id))
                },
                onNavigateToAdd = {
                    navController.navigate(NotesScreen.AddNote())
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<NotesScreen.AddNote> { entry ->
            val note = entry.toRoute<NotesScreen.AddNote>()
            AddNoteScreen(
                idIngredients = note.idIngredient,
                name = note.name,
                paddingValues = paddingValues,
                onSearchIngredientNote = {
                    navController.navigate(NotesScreen.Search)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<NotesScreen.Search> {
            SearchNoteScreen(
                paddingValues = paddingValues,
                onNavigateToAddNote = { idIngredient, name ->
                    navController.navigate(NotesScreen.AddNote(idIngredient, name)) {
                        popUpTo(NotesScreen.Notes) { inclusive = false }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.assessNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation<RootScreen.AssessNav>(
        startDestination = AssessmentScreen.Start
    ) {
        composable<AssessmentScreen.Start> {
            StartAssessmentScreen(
                paddingValues = paddingValues,
                onNavigateToNext = {navController.navigate(AssessmentScreen.Skin)},
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<AssessmentScreen.Skin> {
            SkinAssessmentScreen(
                paddingValues = paddingValues,
                onNextClick = { navController.navigate(AssessmentScreen.Purpose) },
                onBackClick = { navController.popBackStack() },
            )
        }
        composable<AssessmentScreen.Purpose> {
            PurposeAssessmentScreen(
                paddingValues = paddingValues,
                onNextClick = { navController.navigate(AssessmentScreen.Function) },
                onBackClick = { navController.popBackStack() },
            )
        }
        composable<AssessmentScreen.Function> {
            FunctionAssessmentScreen(
                paddingValues = paddingValues,
                onNextClick = { navController.navigate(AssessmentScreen.Allergy) },
                onBackClick = { navController.popBackStack() },
            )
        }
        composable<AssessmentScreen.Allergy> {
            AllergyAssessmentScreen(
                paddingValues = paddingValues,
                onNextClick = { navController.navigate(AssessmentScreen.Result) },
                onBackClick = { navController.popBackStack() },
            )
        }
        composable<AssessmentScreen.Result> {
            AssessmentResultScreen(
                paddingValues = paddingValues,
                onBackClick = { navController.popBackStack() },
                onNavigateToDetail = {
                    navController.navigate(DictionaryScreen.Detail)
                },
            )
        }
    }
}

fun NavGraphBuilder.dictionaryNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation<RootScreen.DictionaryNav>(
        startDestination = DictionaryScreen.Dictionary(rating = null, benefit = null)
    ) {
        composable<DictionaryScreen.Dictionary> { entry ->
            val dictionary = entry.toRoute<DictionaryScreen.Dictionary>()
            DictionaryScreen(
                rating = dictionary.rating,
                benefit = dictionary.benefit,
                paddingValues = paddingValues,
                onNavigateToDetail = { id ->
                    navController.navigate(DictionaryScreen.Detail(id))
                },
                onNavigateToFilter = {
                    navController.navigate(DictionaryScreen.Filter)
                },
            )
        }
        composable<DictionaryScreen.Detail> { entry ->
            val detailIngredient = entry.toRoute<DictionaryScreen.Detail>()
            DictDetailScreen(
                id = detailIngredient.id,
                paddingValues = paddingValues,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<DictionaryScreen.Filter> {
            DictFilterScreen(
                paddingValues = paddingValues,
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigateToDictionary = { rating, benefit ->
                    navController.navigate(DictionaryScreen.Dictionary(rating, benefit))
                }
            )
        }
    }
}

fun NavGraphBuilder.productNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation<RootScreen.ProductNav>(
        startDestination = ProductScreen.Product(skinType = null, category = null)
    ) {
        composable<ProductScreen.Product> { entry ->
            val product = entry.toRoute<ProductScreen.Product>()
            ProductScreen(
                skinType = product.skinType,
                category = product.category,
                paddingValues = paddingValues,
                onNavigateToDetail = { id ->
                    navController.navigate(ProductScreen.Detail(id))
                },
                onNavigateToFilter = {
                    navController.navigate(ProductScreen.Filter)
                }
            )
        }
        composable<ProductScreen.Detail> { entry ->
            val detailProduct = entry.toRoute<ProductScreen.Detail>()
            ProductDetailScreen(
                id = detailProduct.id,
                paddingValues = paddingValues,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<ProductScreen.Filter> {
            ProductFilterScreen(
                paddingValues = paddingValues,
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigateToProduct = { skinType, category ->
                    navController.navigate(ProductScreen.Product(skinType, category))
                }
            )
        }
    }
}

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation<RootScreen.ProfileNav>(
        startDestination = ProfileScreen.Profile
    ) {
        composable<ProfileScreen.Profile> {
            ProfileScreen(
                onLogOut = {
                    navController.navigate(AuthScreen.Login) {
                        popUpTo(ProfileScreen.Profile) { inclusive = true }
                    }
                },
                onNavigateToDeleteAccount = {
                    navController.navigate(ProfileScreen.Delete)
                }
            )
        }
        composable<ProfileScreen.Delete> {
            DeleteAccountScreen(
                onNavigateToLogin = {
                    navController.navigate(AuthScreen.Login) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },
            )
        }
    }
}