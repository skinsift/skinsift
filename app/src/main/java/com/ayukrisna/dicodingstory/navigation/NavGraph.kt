package com.ayukrisna.dicodingstory.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ayukrisna.dicodingstory.view.ui.screen.login.LoginScreen
import com.ayukrisna.dicodingstory.view.ui.screen.signup.SignupScreen

@Composable
fun NavGraph (
    navController: NavHostController,
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
                }
            )
        }
        composable<AuthScreen.SignupScreen> {
            SignupScreen(
                onNavigateToLogin = {
                    navController.navigate(AuthScreen.LoginScreen)
                }
            )
        }
    }
}