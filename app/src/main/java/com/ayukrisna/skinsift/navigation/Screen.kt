package com.ayukrisna.skinsift.navigation

import com.ayukrisna.skinsift.R
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class AuthScreen {
    @Serializable
    data object LoginScreen : AuthScreen()

    @Serializable
    data object SignupScreen : AuthScreen()
}

@Serializable
sealed class AppScreen(val route: String, val label: String, @Contextual val activeIcon: Int, @Contextual val inactiveIcon: Int) {
    @Serializable
    data object HomeScreen : AppScreen(
        route = "home_screen",
        label = "Home",
        activeIcon = R.drawable.filled_home,
        inactiveIcon = R.drawable.outline_home
    )

    @Serializable
    data object DictionaryScreen : AppScreen(
        route = "dictionary_screen",
        label = "Dictionary",
        activeIcon = R.drawable.filled_book,
        inactiveIcon = R.drawable.outline_book
    )

    @Serializable
    data object ProductScreen : AppScreen(
        route = "product_screen",
        label = "Product",
        activeIcon = R.drawable.filled_soap,
        inactiveIcon = R.drawable.outline_soap
    )

    @Serializable
    data object ProfileScreen : AppScreen(
        route = "profile_screen",
        label = "Product",
        activeIcon = R.drawable.filled_soap,
        inactiveIcon = R.drawable.outline_soap
    )
}
