package com.ayukrisna.skinsift.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ayukrisna.skinsift.view.ui.screen.login.LoginScreen
import com.ayukrisna.skinsift.view.ui.screen.signup.SignupScreen

@Composable
fun NavGraph (
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController,
        startDestination = AuthScreen.LoginScreen,
        enterTransition = { fadeIn(tween(100))},
        popEnterTransition = {EnterTransition.None},
        exitTransition = { fadeOut(tween(100))},
        popExitTransition = {ExitTransition.None}
    ) {
        composable<AuthScreen.LoginScreen> {
            LoginScreen(
                onNavigateToSignup = {
                    navController.navigate(AuthScreen.SignupScreen)
                },
                paddingValues = paddingValues
            )
        }
        composable<AuthScreen.SignupScreen> {
            SignupScreen(
                onNavigateToLogin = {
                    navController.navigate(AuthScreen.LoginScreen)
                },
                paddingValues = paddingValues
            )
        }
    }
}