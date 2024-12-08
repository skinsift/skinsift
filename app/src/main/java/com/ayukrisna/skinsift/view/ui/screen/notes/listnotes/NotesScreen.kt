package com.ayukrisna.skinsift.view.ui.screen.notes.listnotes

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.data.remote.response.ingredients.IngredientListItem
import com.ayukrisna.skinsift.data.remote.response.notes.Note
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import com.ayukrisna.skinsift.view.ui.component.ErrorLayout
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import com.ayukrisna.skinsift.view.ui.component.getRatingColor
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
    val deleteNoteState by notesViewModel.deleteNoteState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect (Unit) {
        notesViewModel.fetchNotes()
    }

    LaunchedEffect(deleteNoteState) {
        when (val result = deleteNoteState) {
            is Result.Success -> {
                Toast.makeText(context, "Note sudah dihapus.", Toast.LENGTH_SHORT).show()
                notesViewModel.fetchNotes()
            }
            is Result.Error -> {
                Toast.makeText(context, "Note gagal dihapus: ${result.error}", Toast.LENGTH_LONG).show()
                Log.d("Notes", "Notes gagal dihapus: ${result.error}")
            }
            else -> Unit
        }
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
                                            IngredientsNoteItem(
                                                item = ingredient,
                                                onNavigateToDetail = { onNavigateToDetail(item.id!!) },
                                                onDelete = { id -> notesViewModel.deleteNote(id) }
                                            )
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
                                            IngredientsNoteItem(
                                                item = ingredient,
                                                onNavigateToDetail = { onNavigateToDetail(item.id!!) },
                                                onDelete = { id -> notesViewModel.deleteNote(id) }
                                            )
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
                            ErrorLayout(error = error)
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun IngredientsNoteItem(
    item: IngredientListItem,
    onNavigateToDetail: () -> Unit,
    onDelete: (Int) -> Unit
) {
    val ratingColor = item.rating?.let { getRatingColor(it) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(16.dp)
                    .clickable { onNavigateToDetail() },
            ) {
                item.rating?.let {
                    if (ratingColor != null) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = ratingColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                item.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                item.category?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Item",
                modifier = Modifier
                    .weight(0.5f)
                    .size(24.dp)
                    .clickable { onDelete(item.idIngredients) },
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        }
    }
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