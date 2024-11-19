package com.ayukrisna.dicodingstory.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ayukrisna.dicodingstory.application.ui.view.login.LoginScreen
import com.ayukrisna.dicodingstory.application.ui.theme.DicodingStoryTheme
import com.ayukrisna.dicodingstory.application.ui.view.login.LoginViewModel
import com.ayukrisna.dicodingstory.application.ui.view.signup.SignupScreen
import com.ayukrisna.dicodingstory.application.ui.view.signup.SignupViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DicodingStoryTheme {
                    LoginScreen()
            }
        }
    }
}
