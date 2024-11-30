package com.ayukrisna.skinsift.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayukrisna.skinsift.R

data class TopLevelRoute<T : Any>(
    val title: String,
    val route: T,
    val activeIcon: Int,
    val inactiveIcon: Int,
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation (
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
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

        topLevelRoutes.forEach{ topLevelRoute ->
            val isSelected = currentDestination?.route == topLevelRoute.route
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == topLevelRoute.route } == true,
                icon = { painterResource(id =
                if (isSelected) {
                    topLevelRoute.activeIcon
                } else {
                    topLevelRoute.inactiveIcon
                }) },
                onClick = {
                    navController.navigate(topLevelRoute.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

    }
}

//@Composable
//fun DebugBottomBar() {
//    BottomNavigation {
//        BottomNavigationItem(
//            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
//            selected = false,
//            onClick = {}
//        )
//        BottomNavigationItem(
//            icon = { Icon(Icons.Default.Edit, contentDescription = "Profile") },
//            selected = false,
//            onClick = {}
//        )
//    }
//}