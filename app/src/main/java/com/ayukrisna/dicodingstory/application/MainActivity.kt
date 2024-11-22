package com.ayukrisna.dicodingstory.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ayukrisna.dicodingstory.MyApp
import com.ayukrisna.dicodingstory.application.ui.theme.DicodingStoryTheme
import com.ayukrisna.dicodingstory.application.ui.view.signup.SignupScreen
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DicodingStoryTheme {
                MyApp()
                SignupScreen()
            }
        }
    }
}
