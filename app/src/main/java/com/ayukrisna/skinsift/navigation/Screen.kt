package com.ayukrisna.skinsift.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class RootScreen {
    @Serializable
    object AuthNav
    @Serializable
    object HomeNav
    @Serializable
    object AssessNav
    @Serializable
    object DictionaryNav
    @Serializable
    object ProductNav
    @Serializable
    object ProfileNav
}


@Serializable
sealed class AuthScreen {
    @Serializable
    data object Login : AuthScreen()

    @Serializable
    data object Signup : AuthScreen()
}

@Serializable
sealed class HomeScreen() {
    @Serializable
    data object Home : HomeScreen()

    @Serializable
    data object Preference : HomeScreen()

    @Serializable
    data object AddPreference : HomeScreen()
}

@Serializable
sealed class AssessmentScreen() {
    @Serializable
    data object Start : AssessmentScreen()

    @Serializable
    data object Skin : AssessmentScreen()

    @Serializable
    data object Purpose : AssessmentScreen()

    @Serializable
    data object Function : AssessmentScreen()

    @Serializable
    data object Allergy : AssessmentScreen()

    @Serializable
    data object Result : AssessmentScreen()
}

@Serializable
sealed class DictionaryScreen() {
    @Serializable
    data object Dictionary : DictionaryScreen()

    @Serializable
    data class Detail(val id: Int)

    @Serializable
    data object Filter : DictionaryScreen()
}

@Serializable
sealed class ProductScreen() {
    @Serializable
    data object Product : ProductScreen()

    @Serializable
    data object Detail : ProductScreen()
}

@Serializable
sealed class ProfileScreen() {
    @Serializable
    data object Profile : ProfileScreen()
}