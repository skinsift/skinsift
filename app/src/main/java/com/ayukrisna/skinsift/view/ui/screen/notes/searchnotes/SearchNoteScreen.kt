package com.ayukrisna.skinsift.view.ui.screen.notes.searchnotes

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.data.remote.response.ingredients.IngredientListItem
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import com.ayukrisna.skinsift.view.ui.component.ErrorLayout
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import com.ayukrisna.skinsift.view.ui.component.getRatingColor
import com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary.DictionaryViewModel
import com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary.SearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchNoteScreen(
    paddingValues: PaddingValues,
    dictionaryViewModel: DictionaryViewModel = koinViewModel(),
    searchNotesViewModel: SearchNoteViewModel = koinViewModel(),
    onNavigateToAddNote: (Int, String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ingredientsState by dictionaryViewModel.ingredientsState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        dictionaryViewModel.fetchIngredients()
    }

    Scaffold(
        topBar = {
            SearchNoteAppBar("Pilih Bahan Skincare", onBackClick)
        },
        floatingActionButton = {
            SaveIngredientFab (
                context = context,
                onClick = {
                    val selectedIngredient = searchNotesViewModel.selectedIngredient.value

                    if (selectedIngredient != null) {
                        val idIngredients: Int = selectedIngredient.idIngredients
                        val name: String = selectedIngredient.name!!

                        onNavigateToAddNote(idIngredients, name)
                    } else {
                        (Toast.makeText(context, "Oops! Pilih dulu ingredientsnya!", Toast.LENGTH_LONG).show())
                    }
                }
            )
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
        content = { innerPadding ->
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {
                Spacer(modifier = Modifier.height(108.dp))
                SearchBar { query ->
                    dictionaryViewModel.searchIngredients(
                        query,
                        rating = null,
                        benefit = null
                    )
                }
                when (ingredientsState) {
                    is Result.Idle -> Text("Idle State")
                    is Result.Loading -> LoadingProgress()
                    is Result.Success -> {
                        val ingredients: List<IngredientListItem> = (ingredientsState as Result.Success<List<IngredientListItem>>).data
                        if (ingredients.isNotEmpty()) {
                            ChooseIngredient(
                                ingredientList = ingredients,
                                onSelectIngredient = { ingredient ->
                                    searchNotesViewModel.selectIngredient(ingredient)
                                },
                                viewModel = searchNotesViewModel
                            )
                        } else {
                            Text("Yah, belum ada bahan di sini!")
                        }
                    }
                    is Result.Error -> {
                        val error = (ingredientsState as Result.Error).error
                        ErrorLayout(error = error)
                    }
                }
            }
        }
    )
}

@Composable
fun ChooseIngredient(ingredientList: List<IngredientListItem>,
                     onSelectIngredient: (IngredientListItem) -> Unit,
                     viewModel: SearchNoteViewModel) {
    val selectedItem by viewModel.selectedIngredient.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(ingredientList) { item ->
            val isSelected = selectedItem == item
            val ratingColor = item.rating?.let { getRatingColor(it) }
            Card (
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                    if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                    else MaterialTheme.colorScheme.surfaceBright
                ),
                shape = RoundedCornerShape(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickable {
                            onSelectIngredient(item) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = {
                            onSelectIngredient(item)}
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
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
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SearchNoteAppBar(title: String, onBackClick: () -> Unit, ) {
    CenterAppBar(
        title = title,
        onBackClick = {onBackClick()}
    )
}

@Composable
fun SaveIngredientFab(context: Context, onClick: () -> Unit) {
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