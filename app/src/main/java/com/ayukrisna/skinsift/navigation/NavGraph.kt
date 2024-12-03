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
import com.ayukrisna.skinsift.view.ui.screen.dictionary.DictDetailScreen
import com.ayukrisna.skinsift.view.ui.screen.dictionary.DictFilterScreen
import com.ayukrisna.skinsift.view.ui.screen.dictionary.DictionaryScreen
import com.ayukrisna.skinsift.view.ui.screen.home.HomeScreen
import com.ayukrisna.skinsift.view.ui.screen.login.LoginScreen
import com.ayukrisna.skinsift.view.ui.screen.preference.AddPreferenceScreen
import com.ayukrisna.skinsift.view.ui.screen.preference.PreferenceScreen
import com.ayukrisna.skinsift.view.ui.screen.product.ProductDetailScreen
import com.ayukrisna.skinsift.view.ui.screen.product.ProductScreen
import com.ayukrisna.skinsift.view.ui.screen.profile.ProfileScreen
import com.ayukrisna.skinsift.view.ui.screen.signup.SignupScreen

@Composable
fun NavGraph (
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController,
        startDestination = RootScreen.HomeNav,
        enterTransition = { fadeIn(tween(100))},
        popEnterTransition = {EnterTransition.None},
        exitTransition = { fadeOut(tween(100))},
        popExitTransition = {ExitTransition.None}
    ) {
        authNavGraph(navController, paddingValues)
        homeNavGraph(navController, paddingValues)
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
                onNavigateToPreference = {
                    navController.navigate(HomeScreen.Preference)
                }
            )
        }
        composable<HomeScreen.Preference> {
            PreferenceScreen(
                paddingValues = paddingValues,
                onNavigateToDetail = {
                    navController.navigate(DictionaryScreen.Detail)
                },
                onNavigateToAdd = {
                    navController.navigate(HomeScreen.AddPreference)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<HomeScreen.AddPreference> {
            AddPreferenceScreen(
                paddingValues = paddingValues,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.dictionaryNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation<RootScreen.DictionaryNav>(
        startDestination = DictionaryScreen.Dictionary
    ) {
        composable<DictionaryScreen.Dictionary> {
            DictionaryScreen(
                paddingValues = paddingValues,
                onNavigateToDetail = {
                    navController.navigate(DictionaryScreen.Detail)
                },
                onNavigateToFilter = {
                    navController.navigate(DictionaryScreen.Filter)
                },
            )
        }
        composable<DictionaryScreen.Detail> {
            DictDetailScreen(
                paddingValues = paddingValues,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<DictionaryScreen.Filter> {
            DictFilterScreen(
                paddingValues = paddingValues,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.productNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    navigation<RootScreen.ProductNav>(
        startDestination = ProductScreen.Product
    ) {
        composable<ProductScreen.Product> {
            ProductScreen(
                paddingValues = paddingValues,
                onNavigateToDetail = {
                    navController.navigate(ProductScreen.Detail)
                }
            )
        }
        composable<ProductScreen.Detail> {
            ProductDetailScreen(
                paddingValues = paddingValues,
                onBackClick = { navController.popBackStack() }
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
            ProfileScreen()
        }
    }
}