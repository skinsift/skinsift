package com.ayukrisna.skinsift.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class RootScreen {
    @Serializable
    object AuthNav
    @Serializable
    object HomeNav
    @Serializable
    object NotesNav
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
sealed class HomeScreen {
    @Serializable
    data object Home : HomeScreen()
}

@Serializable
sealed class NotesScreen{
    @Serializable
    data object Notes : NotesScreen()

    @Serializable
    data class AddNote(val idIngredient: Int? = null, val name: String? = null)

    @Serializable
    data object Search : NotesScreen()
}

@Serializable
sealed class AssessmentScreen {
    @Serializable
    data object Start : AssessmentScreen()

    @Serializable
    data object Assessment : AssessmentScreen()

    @Serializable
    data class Result(val productItem: String, val isHamil: Boolean)
}

@Serializable
sealed class DictionaryScreen {
    @Serializable
    data class Dictionary(val rating: List<String>? = null, val benefit: List<String>? = null)

    @Serializable
    data class Detail(val id: Int)

    @Serializable
    data object Filter : DictionaryScreen()
}

@Serializable
sealed class ProductScreen {
    @Serializable
    data class Product(val skinType: List<String>? = null, val category: List<String>? = null)

    @Serializable
    data class Detail(val id: Int)

    @Serializable
    data object Filter : ProductScreen()
}

@Serializable
sealed class ProfileScreen {
    @Serializable
    data object Profile : ProfileScreen()

    @Serializable
    data object Delete : ProfileScreen()
}