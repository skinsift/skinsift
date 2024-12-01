package com.ayukrisna.skinsift.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
fun BottomNavigationBar(
    navController: NavController
) {
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination

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
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        topLevelRoutes.forEachIndexed{ index, topLevelRoute ->
            BottomNavigationItem(
                selected = selectedItemIndex == index,
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (index == selectedItemIndex) {
                                topLevelRoute.activeIcon
                            } else {
                                topLevelRoute.inactiveIcon
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
//                label = {
//                    Text(text = topLevelRoute.title)
//                },
                onClick = {
                    selectedItemIndex = index
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