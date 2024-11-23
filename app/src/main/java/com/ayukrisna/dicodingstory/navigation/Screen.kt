package com.ayukrisna.dicodingstory.navigation

import kotlinx.serialization.Serializable

//@Serializable
//sealed class RootScreen {
//    @Serializable
//    object AuthNav
//}

@Serializable
sealed class AuthScreen {
    @Serializable
    data object LoginScreen : AuthScreen()

    @Serializable
    data object SignupScreen : AuthScreen()
}
