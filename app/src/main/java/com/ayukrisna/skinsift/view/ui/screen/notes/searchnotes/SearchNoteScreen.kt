package com.ayukrisna.skinsift.view.ui.screen.notes.searchnotes

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.data.remote.response.ingredients.IngredientListItem
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import com.ayukrisna.skinsift.view.ui.component.getRatingColor
import com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary.DictionaryViewModel
import com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary.SearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchNoteScreen(
    paddingValues: PaddingValues,
    dictionaryViewModel: DictionaryViewModel = koinViewModel(),
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
                            ChooseIngredient(ingredients)
                        } else {
                            Text("Yah, belum ada bahan di sini!")
                        }
                    }
                    is Result.Error -> {
                        val error = (ingredientsState as Result.Error).error
                        Text("Error: $error")
                        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    )
}

@Composable
fun ChooseIngredient(ingredientList: List<IngredientListItem>) {
    var selectedItem by remember { mutableStateOf(ingredientList[0]) }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(ingredientList) { item ->
            val isSelected = selectedItem == item
            val ratingColor = item.rating?.let { getRatingColor(it) }
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp))
                    .border(
                        width = 0.5.dp,
                        color = if (selectedItem == item) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceBright,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { selectedItem = item },
                colors = CardDefaults.cardColors(
                    containerColor =
                    if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                    else MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    RadioButton(
                        selected = selectedItem == item,
                        onClick = { selectedItem = item }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNoteAppBar(title: String, onBackClick: () -> Unit, ) {
    CenterAppBar(
        title = title,
        onBackClick = {onBackClick()}
    )
}