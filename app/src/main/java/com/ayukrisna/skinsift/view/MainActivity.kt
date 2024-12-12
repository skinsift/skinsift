package com.ayukrisna.skinsift.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ayukrisna.skinsift.MyApp
import com.ayukrisna.skinsift.view.ui.MainScreen
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(0xffedf0f7.toInt(), 0xffedf0f7.toInt())
        )
        setContent {
            SkinSiftTheme {
                MyApp()
                MainScreen()
            }
        }
    }
}