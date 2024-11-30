package com.ayukrisna.skinsift.view.ui.bottomnav

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.ayukrisna.skinsift.navigation.AppScreen


@Composable
fun BottomNav(
    items: List<AppScreen>,
    currentRoute: String?,
    onItemSelected: (AppScreen) -> Unit,
    navController: NavHostController
) {
    BottomNavigation (
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            BottomNavigationItem(
                selected = isSelected,
                onClick = { onItemSelected(screen) },
                icon = { painterResource(
                    id = if (isSelected) screen.activeIcon else screen.inactiveIcon
                ) },
                label = { Text(screen.label) }
            )

        }
    }
}