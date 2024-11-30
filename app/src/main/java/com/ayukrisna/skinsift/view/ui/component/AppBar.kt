package com.ayukrisna.skinsift.view.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight


/**
 * AppBar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    subtitle: String,
    actionIcon: ImageVector? = null,
    onActionClick: (() -> Unit)? = null) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier
            ) {
                Text(text = title,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            if (actionIcon != null && onActionClick != null) {
                IconButton(onClick = { onActionClick() }) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = "Action",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAppBar(
    title: String,
    actionIcon: ImageVector? = null,
    onActionClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)?  = null
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                if (onBackClick != null) {
                    onBackClick()
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            if (actionIcon != null && onActionClick != null) {
                IconButton(onClick = { onActionClick() }) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = "Action",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
}