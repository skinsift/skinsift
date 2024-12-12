package com.ayukrisna.skinsift.view.ui.screen.notes.addnotes

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import org.koin.androidx.compose.koinViewModel
import com.ayukrisna.skinsift.util.Result

@Composable
fun AddNoteScreen(
    idIngredients: Int? = null,
    name: String? = null,
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onSearchIngredientNote: () -> Unit,
    viewModel: AddNoteViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val addNoteState by viewModel.addNoteState.observeAsState(initial = Result.Loading)
    var selectedPreference by rememberSaveable { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AddPreferenceAppBar("Tambah Bahan Skincare", onBackClick)
        },
        floatingActionButton = {
            SavePreferenceFab(
                context = context,
                onClick = {
                    if (idIngredients != null && selectedPreference != null) {
                        viewModel.addNote(
                            idIngredient = idIngredients,
                            preference = selectedPreference!!
                        )
                    } else {
                        Toast.makeText(context, "Oops! Isi dulu bahan dan preferensimu!", Toast.LENGTH_LONG).show()
                    }
                }
            )
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
                ChooseIngredient(
                    onSearchIngredientNote = onSearchIngredientNote,
                    ingredientName = name
                )

                ChoosePreferenceDropDown(
                    onPreferenceSelected = { preference ->
                        selectedPreference = preference
                    }
                )
                when (addNoteState) {
                    is Result.Idle -> {}
                    is Result.Loading -> Text("Uploading...")
                    is Result.Success<*> -> {
                        var showDialog by remember { mutableStateOf(true) }

                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text(text = "Success") },
                                text = { Text("Yeay! Notesmu sudah ditambahkan")},
                                confirmButton = {
                                    Button(onClick = {
                                        showDialog = false
                                        onBackClick()
                                    }) {
                                        Text("OK")
                                    }
                                }
                            )
                        }
                    }
                    is Result.Error -> {
                        val error = (addNoteState as Result.Error).error
                        Text("Error: $error", color = Color.Red)
                    }
                }
            }
        }
    )
}

@Composable
fun ChooseIngredient(
    onSearchIngredientNote: () -> Unit,
    ingredientName: String? = null,
) {
    Text(
        text = "Nama Bahan",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(8.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceBright)
            .height(54.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .padding(8.dp)
            .clickable { onSearchIngredientNote() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = ingredientName ?: "Cari nama bahan",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoosePreferenceDropDown(onPreferenceSelected: (String) -> Unit) {
    val options = listOf("Suka", "Tidak Suka")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Text(
        text = "Preferensi Bahan",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        TextField(
            value = selectedOption ?: "Pilih preferensi",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceBright, shape = RoundedCornerShape(20.dp))
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), RoundedCornerShape(20.dp)),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceBright
            ),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        onPreferenceSelected(option)
                        expanded = false
                    },
                )
            }
        }
    }
}



@Composable
fun AddPreferenceAppBar(title: String, onBackClick: () -> Unit, modifier: Modifier = Modifier
) {
    CenterAppBar( title = title, onBackClick = {onBackClick()}
    )
}

@Composable
fun SavePreferenceFab(context: Context, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Simpan Catatan",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}