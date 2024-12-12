package com.ayukrisna.skinsift.view.ui.screen.profile.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.domain.model.UserModel
import com.ayukrisna.skinsift.view.ui.component.AppBar
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    onLogOut: () -> Unit,
    onNavigateToDeleteAccount: () -> Unit,
    modifier: Modifier = Modifier
){
    val userData by viewModel.userData.collectAsState()
    val showLogoutDialog = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ProfileAppBar("Profile", "Profile Kerenmu")
        },
        content = { paddingValues->
            // Padding values should be applied if needed
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
            ) {
                userData?.let { user ->
                    ProfileBar(user)
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileButton("Keluar dari aplikasi", {showLogoutDialog.value = true}, painterResource(id = R.drawable.ic_logout))
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileButton("Hapus akun", {showDeleteDialog.value = true}, painterResource(id = R.drawable.ic_delete_forever))
                } ?: LoadingProgress()
                Spacer(modifier = Modifier.height(12.dp))
            }
            // Logout alert
            ProfileAlertDialog(
                showDialog = showLogoutDialog,
                title = "Keluar dari aplikasi",
                actionDesc = "Apakah kamu yakin ingin keluar dari aplikasi?",
                confirmDesc= "Keluar",
                cancelDesc= "Batal",
                onConfirm = {
                    viewModel.logOut()
                    onLogOut()
                },
                onDismiss = {}
            )
            // Delete account alert
            ProfileAlertDialog(
                showDialog = showDeleteDialog,
                title = "Hapus akun",
                actionDesc = "Apakah kamu yakin ingin menghapus akunmu?",
                confirmDesc= "Hapus Akun",
                cancelDesc= "Batal",
                onConfirm = {
                    onNavigateToDeleteAccount()
                },
                onDismiss = {}
            )
        }
    )
}

@Composable
fun ProfileButton(optionName: String, onClick: () -> Unit, actionIcon: Painter) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = actionIcon,
                contentDescription = "Action icon",
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = optionName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ProfileBar(user: UserModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Informasi Pribadi",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            ProfileItem("Username", user.username)
            ProfileItem("Email", user.email)
            ProfileItem("Password", "************")
        }
    }
}

@Composable
fun ProfileItem(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(3f)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun ProfileAppBar(title: String, subtitle: String) {
    AppBar(title, subtitle)
}

@Composable
fun ProfileAlertDialog(
    showDialog: MutableState<Boolean>,
    title: String,
    actionDesc: String,
    confirmDesc: String,
    cancelDesc: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = actionDesc)
            },
            confirmButton = {
                Button(onClick = {
                    showDialog.value = false
                    onConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                    Text(text = confirmDesc)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        onDismiss()
                    }
                ) {
                    Text(cancelDesc, color = MaterialTheme.colorScheme.secondary)
                }
            }
        )
    }
}