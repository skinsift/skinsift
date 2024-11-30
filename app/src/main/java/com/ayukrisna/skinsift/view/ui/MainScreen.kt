package com.ayukrisna.skinsift.view.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ayukrisna.skinsift.navigation.BottomNavigationBar
import com.ayukrisna.skinsift.navigation.NavGraph

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
//DebugBottomBar()
        }
    ) { innerPadding ->
//        Text("Test Content", modifier = Modifier.padding(innerPadding))
        NavGraph(
            navController = navController,
            paddingValues = PaddingValues(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current) + 16.dp,
                top = innerPadding.calculateTopPadding() + 32.dp,
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current) + 16.dp,
                bottom = innerPadding.calculateBottomPadding() + 32.dp
            )
        )
    }
}