package com.ayukrisna.skinsift.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ayukrisna.skinsift.MyApp
import com.ayukrisna.skinsift.navigation.NavGraph
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkinSiftTheme {
                MyApp()
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
