package com.ayukrisna.dicodingstory.application.ui.view.signup

import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ayukrisna.dicodingstory.R
import com.ayukrisna.dicodingstory.application.ui.component.CustomTextField
import com.ayukrisna.dicodingstory.application.ui.theme.DicodingStoryTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun SignupScreen(
    viewModel: SignupViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val signUpState by viewModel.signUpState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

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
            Text("Ayo Signup",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 42.dp))
            //Name Input Field
            NameTextField(viewModel)
            //Email Input Field
            EmailTextField(viewModel)
            //Password Input Field
            PasswordTextField(viewModel)
            //Sign up Button
            SignupButton(viewModel)

            when {
                signUpState != null -> {
                    Text("Sign Up Successful: $signUpState")
                    LaunchedEffect(signUpState) {
                        println("Resetting signUpState")
                        kotlinx.coroutines.delay(3000)
                        viewModel.resetStates()
                    }
                }
                errorState != null -> {
                    Text("Error: $errorState", color = Color.Red)
                    LaunchedEffect(errorState) {
                        println("Resetting errorState")
                        kotlinx.coroutines.delay(3000)
                        viewModel.resetStates()
                    }
                }
            }
        }
    }
}

@Composable
fun SignupButton(
    viewModel: SignupViewModel,
    modifier: Modifier = Modifier
){
    Button(onClick = {
        viewModel.onEvent(SignupEvent.Submit)
    },
        modifier = Modifier.fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 8.dp)) {
        Text("Sign Up")
    }
}

@Composable
fun NameTextField(viewModel: SignupViewModel) {
    CustomTextField(
        title = "Nama",
        text = viewModel.formState.name,
        onValueChange = {
            viewModel.onEvent(SignupEvent.NameChanged(it))
        },
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next,
        isError = viewModel.formState.nameError != null,
        errorMessage = viewModel.formState.nameError,
        singleLine = true,
    )
}

@Composable
fun EmailTextField(viewModel: SignupViewModel) {
    CustomTextField(
        title = "Email",
        text = viewModel.formState.email,
        onValueChange = {
            viewModel.onEvent(SignupEvent.EmailChanged(it))
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
    viewModel: SignupViewModel
) {
    DicodingStoryTheme {
        CustomTextField(
            title = "Password",
            text = viewModel.formState.password,
            onValueChange = {
                viewModel.onEvent(SignupEvent.PasswordChanged(it))
            },
            leadingIcon = painterResource(id = R.drawable.ic_lock),
            trailingIcon = {
                Box(
                    modifier = Modifier
                ) {
                    IconButton(
                        onClick =
                        {
                            viewModel.onEvent(SignupEvent.VisiblePassword(!(viewModel.formState.isVisiblePassword)))
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
