package com.ayukrisna.skinsift.view.ui.screen.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar

@Composable
fun AddPreferenceScreen(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AddPreferenceAppBar("Tambah Bahan Skincare", onBackClick)
        },
        floatingActionButton = {
            SavePreferenceFab(onBackClick)
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        bottom = paddingValues.calculateBottomPadding() - 32.dp)
            ) {

            }
        }
    )
}

@Composable
fun AddPreferenceAppBar(title: String, onBackClick: () -> Unit, modifier: Modifier = Modifier
) {
    CenterAppBar( title = title, onBackClick = {onBackClick()}
    )
}

@Composable
fun SavePreferenceFab(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Simpan Bahan Skincare",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}