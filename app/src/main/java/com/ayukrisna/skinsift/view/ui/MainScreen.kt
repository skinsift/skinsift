package com.ayukrisna.skinsift.view.ui

import android.util.Log
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

    val noBarRoutes = setOf(
        "com.ayukrisna.skinsift.navigation.AuthScreen.Splash",
        "com.ayukrisna.skinsift.navigation.AuthScreen.Login",
        "com.ayukrisna.skinsift.navigation.AuthScreen.Signup",

        "com.ayukrisna.skinsift.navigation.NotesScreen.Notes",
        "com.ayukrisna.skinsift.navigation.NotesScreen.Search",

        "com.ayukrisna.skinsift.navigation.AssessmentScreen.Start",
        "com.ayukrisna.skinsift.navigation.AssessmentScreen.Assessment",
        "com.ayukrisna.skinsift.navigation.AssessmentScreen.Result/{productItem}/{isHamil}",

        "com.ayukrisna.skinsift.navigation.OcrScreen.Ocr",

        "com.ayukrisna.skinsift.navigation.DictionaryScreen.Filter",
        "com.ayukrisna.skinsift.navigation.ProductScreen.Filter",

        "com.ayukrisna.skinsift.navigation.DictionaryScreen.Detail/{id}",
        "com.ayukrisna.skinsift.navigation.ProductScreen.Detail/{id}",

        "com.ayukrisna.skinsift.navigation.NotesScreen.AddNote?idIngredient={idIngredient}&name={name}",

        "com.ayukrisna.skinsift.navigation.ProfileScreen.Delete",
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val currentRoute = currentDestination?.route

//            val showBottomBar = noBottomNavRoutes.none {
//                if (currentDestination?.route == null) true
//                else (currentDestination.hasRoute(it::class) && currentRoute !in argumentRoutes)
//            }

            val showBottomBar = currentRoute !in noBarRoutes
            Log.d("Navigation", "Current route: $currentRoute")

//            Log.d("Navigation", "Current route: $currentRoute \n Argument route: $noBarRoutes \n Is in argument: ${currentRoute !in noBarRoutes} \n Show bottom bar: $showBottomBar")
//            Log.d("Navigation", "If currentDestination?.route == null: ${currentDestination?.route == null}")


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