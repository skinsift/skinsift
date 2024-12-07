package com.ayukrisna.skinsift.view.ui.screen.profile.delete

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import com.ayukrisna.skinsift.view.ui.component.CustomTextField
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DeleteAccountScreen(
    viewModel: DeleteAccountViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val deleteState by viewModel.deleteState.observeAsState(initial = Result.Loading)
    Scaffold(
        topBar = {
            DeleteProfileAppBar("Hapus Akun", {onBackClick()})
        },
        content = { paddingValues->
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Yakin ingin menghapus akunmu?", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp, 0.dp, 8.dp))
                Text(
                    text = "Bila kamu sudah benar-benar yakin, lakukan konfirmasi kembali dengan memasukkan passwordmu di bawah ini.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 42.dp))
                PasswordTextField(viewModel)
                DeleteButton(viewModel)
                when (deleteState) {
                    is Result.Idle -> {}
                    is Result.Loading -> Text("Loading...")
                    is Result.Success -> {
                        LaunchedEffect(Unit) {
                            onNavigateToLogin()
                        }
                    }
                    is Result.Error -> {
                        val error = (deleteState as Result.Error).error
                        Text("Error: $error", color = Color.Red)
                    }
                }
            }

        }
    )
}

@Composable
fun PasswordTextField(
    viewModel: DeleteAccountViewModel
) {
    SkinSiftTheme {
        CustomTextField(
            title = "Password",
            placeholder = "Masukkan password di sini",
            text = viewModel.formState.password,
            onValueChange = {
                viewModel.onEvent(DeleteEvent.PasswordChanged(it))
            },
            leadingIcon = painterResource(id = R.drawable.ic_lock),
            trailingIcon = {
                Box(
                    modifier = Modifier) {
                    IconButton(
                        onClick =
                        {
                            viewModel.onEvent(DeleteEvent.VisiblePassword(!(viewModel.formState.isVisiblePassword)))
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

@Composable
fun DeleteButton(
    viewModel: DeleteAccountViewModel,
    modifier: Modifier = Modifier
){
    Button(onClick = {
        viewModel.onEvent(DeleteEvent.Submit)
    },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp, 0.dp, 8.dp)) {
        Text("Hapus Akun")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteProfileAppBar(title: String, onBackClick: () -> Unit,) {
    CenterAppBar(title = title, onBackClick = {onBackClick()})
}