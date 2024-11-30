package com.ayukrisna.skinsift.view.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.view.ui.component.AppBar
import com.ayukrisna.skinsift.view.ui.screen.product.SkincareAppBar

@Composable
fun ProfileScreen(modifier: Modifier = Modifier){
    Scaffold(
        topBar = {
            ProfileAppBar("Profile", "Ini Profile")
        },
        content = { paddingValues->
            // Padding values should be applied if needed
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
            ) {

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAppBar(title: String, subtitle: String) {
    AppBar(title, subtitle)
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}