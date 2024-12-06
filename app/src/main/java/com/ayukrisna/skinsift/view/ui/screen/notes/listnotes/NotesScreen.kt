package com.ayukrisna.skinsift.view.ui.screen.notes.listnotes

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.data.remote.response.ingredients.IngredientListItem
import com.ayukrisna.skinsift.data.remote.response.notes.Note
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary.IngredientsItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen (
    paddingValues: PaddingValues,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToAdd: () -> Unit,
    onBackClick: () -> Unit,
    notesViewModel: NotesViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Suka", "Tidak Suka")

    val notesState by notesViewModel.notesState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect (Unit) {
        notesViewModel.fetchNotes()
    }

    Scaffold (
        topBar = {
            PreferenceAppBar("Catatan Bahan", onBackClick)
        },
        floatingActionButton = {
            AddPreferenceButton({onNavigateToAdd()})
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        bottom = paddingValues.calculateBottomPadding() - 32.dp)
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }

                when (notesState) {
                    is Result.Idle -> Text("Idle State")
                    is Result.Loading -> LoadingProgress()
                    is Result.Success -> {
                        val notes = (notesState as Result.Success<List<Note?>>).data
                        when (selectedTabIndex) {
                            0 -> {
                                val likedNotes = notes.filter { it?.preference == "Suka" }
                                if (likedNotes.isEmpty()) {
                                    NullPreference()
                                } else {
                                    Spacer(modifier = Modifier.height(24.dp))
                                    LazyColumn {
                                        items(likedNotes) { item ->
                                            val ingredient : IngredientListItem =
                                                IngredientListItem(
                                                    idIngredients = item!!.id!!,
                                                    name = item.name,
                                                    rating = item.rating,
                                                    category = item.category,
                                                )
                                            IngredientsItem(ingredient) { onNavigateToDetail(item.id!!) }
                                            Spacer(modifier = Modifier.height(10.dp))
                                        }
                                    }
                                }
                            }
                            1 -> {
                                val dislikedNotes = notes.filter { it?.preference == "Tidak Suka" }
                                if (dislikedNotes.isEmpty()) {
                                    NullPreference()
                                } else {
                                    Spacer(modifier = Modifier.height(24.dp))
                                    LazyColumn {
                                        items(dislikedNotes) { item ->
                                            val ingredient : IngredientListItem =
                                                IngredientListItem(
                                                    idIngredients = item!!.id!!,
                                                    name = item.name,
                                                    rating = item.rating,
                                                    category = item.category,
                                                )
                                            IngredientsItem(ingredient) { onNavigateToDetail(item.id!!) }
                                            Spacer(modifier = Modifier.height(10.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is Result.Error -> {
                        val error = (notesState as Result.Error).error
                        if (error == "No notes found for user") {
                            NullPreference()
                        } else {
                            Text("Error: $error")
                            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun NullPreference() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.skin_care_2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp).graphicsLayer(alpha = 0.5f)
            )
            Text(
                text = "Kamu belum menyimpan ingredients yang kamu suka, nih.\n" +
                        "Yuk, tambahkan dulu!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun PreferenceAppBar(title: String,
                     onBackClick: () -> Unit,
                     modifier: Modifier = Modifier) {
    CenterAppBar( title = title,
        onBackClick = {onBackClick()}
    )
}

@Composable
fun AddPreferenceButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(Icons.Filled.Add, "Add preference action button.") },
        text = { Text("Tambah Notes") },
    )
}