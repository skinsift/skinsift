package com.ayukrisna.dicodingstory.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ayukrisna.dicodingstory.application.ui.screen.LoginScreen
import com.ayukrisna.dicodingstory.application.ui.theme.DicodingStoryTheme
import com.ayukrisna.dicodingstory.application.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: LoginViewModel = LoginViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DicodingStoryTheme {
                    LoginScreen(viewModel = viewModel)
            }
        }
    }
}
