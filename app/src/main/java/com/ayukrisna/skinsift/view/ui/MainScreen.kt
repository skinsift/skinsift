package com.ayukrisna.skinsift.view.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ayukrisna.skinsift.navigation.AuthScreen
import com.ayukrisna.skinsift.navigation.BottomNavigationBar
import com.ayukrisna.skinsift.navigation.NavGraph
import androidx.navigation.NavDestination.Companion.hasRoute
import com.ayukrisna.skinsift.navigation.AssessmentScreen
import com.ayukrisna.skinsift.navigation.DictionaryScreen
import com.ayukrisna.skinsift.navigation.NotesScreen
import com.ayukrisna.skinsift.navigation.ProductScreen

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val noBottomNavRoutes = listOf(
        AuthScreen.Login,
        AuthScreen.Signup,
        NotesScreen.Notes,
        NotesScreen.Search,
        AssessmentScreen.Start,
        AssessmentScreen.Skin,
        AssessmentScreen.Purpose,
        AssessmentScreen.Function,
        AssessmentScreen.Allergy,
        AssessmentScreen.Result,
        DictionaryScreen.Filter,
        ProductScreen.Filter
    )

    val argumentRoutes = setOf(
        "com.ayukrisna.skinsift.navigation.DictionaryScreen.Detail/{id}",
        "com.ayukrisna.skinsift.navigation.ProductScreen.Detail/{id}"
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val currentRoute = currentDestination?.route

            val showBottomBar = noBottomNavRoutes.none {
                if (currentDestination?.route == null) true
                else (currentDestination.hasRoute(it::class) && currentRoute !in argumentRoutes)
            }

            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->

        NavGraph(
            navController = navController,
            paddingValues = PaddingValues (
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current) + 16.dp,
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current) + 16.dp,
                bottom = innerPadding.calculateBottomPadding()
            )
        )
    }
}