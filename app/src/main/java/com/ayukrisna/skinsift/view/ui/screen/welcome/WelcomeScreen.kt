package com.ayukrisna.skinsift.view.ui.screen.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier
) {
    Surface {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 42.dp)
        ) {
            Text("Hai!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp, 0.dp, 8.dp))

            //Log In Button
            LoginButton()
            //Signup Button
            SignupButton()
        }
    }
}

@Composable
fun LoginButton(modifier: Modifier = Modifier){
    Button(onClick = {  },
        modifier = Modifier.fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 4.dp)) {
        Text("Log In")
    }
}

@Composable
fun SignupButton(modifier: Modifier = Modifier){
    OutlinedButton(onClick = {  },
        modifier = Modifier.fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 8.dp)) {
        Text("Sign Up")
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    SkinSiftTheme {
        WelcomeScreen()
    }
}