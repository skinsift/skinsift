package com.ayukrisna.skinsift.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ayukrisna.skinsift.MyApp
import com.ayukrisna.skinsift.navigation.NavGraph
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkinSiftTheme {
                MyApp()
//                HomeScreen()
                val navController = rememberNavController()
                NavGraph(navController = navController,
                    paddingValues = PaddingValues(horizontal = 16.dp, vertical = 42.dp))
            }
        }
    }
}
