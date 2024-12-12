package com.ayukrisna.skinsift.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayukrisna.skinsift.R
import androidx.navigation.NavDestination.Companion.hasRoute

data class TopLevelRoute<T : Any>(
    val title: String,
    val route: T,
    val activeIcon: Int,
    val inactiveIcon: Int,
)

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    BottomNavigation (
        modifier = Modifier.navigationBarsPadding(),
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary

    ){
        val topLevelRoutes = listOf(
            TopLevelRoute(
                title = "Home",
                route = RootScreen.HomeNav,
                activeIcon = R.drawable.filled_home,
                inactiveIcon = R.drawable.outline_home
            ),
            TopLevelRoute(
                title = "Dictionary",
                route = RootScreen.DictionaryNav,
                activeIcon = R.drawable.filled_book,
                inactiveIcon = R.drawable.outline_book
            ),
            TopLevelRoute(
                title = "Product",
                route = RootScreen.ProductNav,
                activeIcon = R.drawable.filled_soap,
                inactiveIcon = R.drawable.outline_soap

            ),
            TopLevelRoute(
                title = "Profile",
                route = RootScreen.ProfileNav,
                activeIcon = R.drawable.filled_profile,
                inactiveIcon = R.drawable.outline_profile
            )
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        topLevelRoutes.map{ topLevelRoute ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { current ->
                    current.hasRoute(topLevelRoute.route::class)} == true,
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (currentDestination?.hierarchy?.any { current ->
                                    current.hasRoute(topLevelRoute.route::class)} == true) {
                                topLevelRoute.activeIcon
                            } else {
                                topLevelRoute.inactiveIcon
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                onClick = {
                    navController.navigate(topLevelRoute.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}