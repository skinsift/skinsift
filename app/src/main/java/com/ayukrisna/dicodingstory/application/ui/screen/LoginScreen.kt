package com.ayukrisna.dicodingstory.application.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayukrisna.dicodingstory.R
import com.ayukrisna.dicodingstory.application.component.CustomTextField
import com.ayukrisna.dicodingstory.application.ui.theme.DicodingStoryTheme
import com.ayukrisna.dicodingstory.application.viewmodel.AuthEvent
import com.ayukrisna.dicodingstory.application.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier,
) {
    Surface {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 42.dp)
        ) {
            // Greetings
            Text("Hai!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp, 0.dp, 8.dp))
            Text("Ayo Login",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 42.dp))

            //Email Input Field
            EmailTextField(viewModel)
            //Password Input Field
            PasswordTextField(viewModel)
            //Log In Button
            LoginButton()
        }
    }
}

@Composable
fun LoginButton(modifier: Modifier = Modifier){
    Button(onClick = {  },
        modifier = Modifier.fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 8.dp)) {
        Text("Sign Up")
    }
}

@Composable
fun EmailTextField(viewModel: LoginViewModel) {
    CustomTextField(
        title = "Email",
        text = viewModel.formState.email,
        onValueChange = {
            viewModel.onEvent(AuthEvent.EmailChanged(it))
        },
        leadingIcon = painterResource(id = R.drawable.ic_email),
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next,
        isError = viewModel.formState.emailError != null,
        errorMessage = viewModel.formState.emailError,
        singleLine = true,
    )
}

@Composable
fun PasswordTextField(
    viewModel: LoginViewModel
) {
    DicodingStoryTheme {
        CustomTextField(
            title = "Password",
            text = viewModel.formState.password,
            onValueChange = {
                viewModel.onEvent(AuthEvent.PasswordChanged(it))
            },
            leadingIcon = painterResource(id = R.drawable.ic_lock),
            trailingIcon = {
                Box(
                    modifier = Modifier) {
                    IconButton(
                        onClick =
                        {
                            viewModel.onEvent(AuthEvent.VisiblePassword(!(viewModel.formState.isVisiblePassword)))
                        }
                    ) {
                        Icon(
                            painter = if (viewModel.formState.isVisiblePassword) painterResource(
                                id = R.drawable.ic_visibility_off
                            ) else painterResource(
                                id = R.drawable.ic_visibility
                            ),
                            contentDescription = "Visible",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .requiredSize(48.dp).padding(16.dp)
                        )
                    }
                }
            },
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            isError =  viewModel.formState.passwordError != null,
            isVisible = viewModel.formState.isVisiblePassword,
            errorMessage = viewModel.formState.passwordError,
            singleLine = true,
        )
    }
}