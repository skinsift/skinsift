package com.ayukrisna.skinsift.view.ui.screen.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.data.remote.response.auth.RegisterResponse
import com.ayukrisna.skinsift.view.ui.component.CustomTextField
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme
import org.koin.androidx.compose.koinViewModel
import com.ayukrisna.skinsift.util.Result

@Composable
fun SignupScreen(
    viewModel: SignupViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val signUpState by viewModel.signUpState.observeAsState(initial = Result.Loading)

    Surface {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding() + 32.dp,
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                )
        ) {
            // Greetings
            Text(
                stringResource(R.string.welcome), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp, 0.dp, 8.dp))
            Text(
                stringResource(R.string.signup_instruction),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 42.dp))
            //Username Input Field
            UsernameTextField(viewModel)
            //Email Input Field
            EmailTextField(viewModel)
            //Password Input Field
            PasswordTextField(viewModel)
            //Sign up Button
            SignupButton(viewModel)
            //Login Button
            LoginButton({ onNavigateToLogin() })

            when (signUpState) {
                is Result.Idle -> {}
                is Result.Loading -> Text("Loading")
                is Result.Success<*> -> {
                    val registerResponse = (signUpState as Result.Success<RegisterResponse>).data
                    Text("Sign up Successful: ${registerResponse.message}")
                }
                is Result.Error -> {
                    val error = (signUpState as Result.Error).error
                    Text("Error: $error", color = Color.Red)
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 8.dp)) {
        Text(stringResource(R.string.signup_btn))
    }
}

@Composable
fun LoginButton(onNavigateToLogin: () -> Unit, modifier: Modifier = Modifier){
    OutlinedButton(onClick = {
        onNavigateToLogin()
    },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 8.dp)) {
        Text(stringResource(R.string.login_btn))
    }
}

@Composable
fun UsernameTextField(viewModel: SignupViewModel) {
    CustomTextField(
        title = "Username",
        placeholder = "Masukkan username kerenmu",
        text = viewModel.formState.username,
        onValueChange = {
            viewModel.onEvent(SignupEvent.UsernameChanged(it))
        },
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next,
        isError = viewModel.formState.usernameError != null,
        errorMessage = viewModel.formState.usernameError,
        singleLine = true,
    )
}

@Composable
fun EmailTextField(viewModel: SignupViewModel) {
    CustomTextField(
        title = "Email",
        placeholder = "emailkeren@gmail.com",
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
    SkinSiftTheme {
        CustomTextField(
            title = "Password",
            placeholder = "Password kerenmu di sini",
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
                                .requiredSize(48.dp)
                                .padding(16.dp)
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
