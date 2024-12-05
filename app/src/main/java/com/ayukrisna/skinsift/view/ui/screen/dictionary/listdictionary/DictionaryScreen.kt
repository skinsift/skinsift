package com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.ayukrisna.skinsift.R
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.view.ui.component.AppBar
import com.ayukrisna.skinsift.view.ui.component.CustomTextField
import com.ayukrisna.skinsift.view.ui.component.getRatingColor
import com.ayukrisna.skinsift.data.remote.response.IngredientListItem
import org.koin.androidx.compose.koinViewModel
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import com.ayukrisna.skinsift.view.ui.screen.dictionary.filterdictionary.DictFilterViewModel

@Composable
fun DictionaryScreen(
    rating : List<String>? = null,
    benefit : List<String>? = null,
    dictViewModel: DictionaryViewModel = koinViewModel(),
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToFilter: () -> Unit,
    paddingValues: PaddingValues,
    modifier : Modifier = Modifier
) {
    val ingredientsState by dictViewModel.ingredientsState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (rating == null && benefit == null) {
            Log.d("DictFilterViewModel", "Selected filters is empty")
            dictViewModel.fetchIngredients()
        } else {
            Log.d("DictFilterViewModel", "Rating: $rating, Benefit: $benefit")
            dictViewModel.searchIngredients(rating, benefit)
        }
    }

    Scaffold(
        topBar = {
            DictionaryAppBar(
                "Kamus Bahan Skincare",
                "Cari yang kamu butuhkan",
                painterResource(id = R.drawable.ic_filter)
            ) { onNavigateToFilter() }
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
                DictionarySearchBar { }
                if (rating != null || benefit != null) {
                    ShowFilter(rating, benefit)
                    Spacer(modifier = Modifier.height(24.dp))
                }
                when (ingredientsState) {
                    is Result.Idle -> Text("Idle State")
                    is Result.Loading -> LoadingProgress()
                    is Result.Success -> {
                        val ingredients: List<IngredientListItem> = (ingredientsState as Result.Success<List<IngredientListItem>>).data
                        if (ingredients.isNotEmpty()) {
                            LazyColumn {
                                items(ingredients) { item ->
                                    IngredientsItem(item) { onNavigateToDetail(item.idIngredients) }
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
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
fun IngredientsItem(
    item: IngredientListItem,
    onNavigateToDetail: () -> Unit
) {
    val ratingColor = item.rating?.let { getRatingColor(it) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToDetail() },
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

@Composable
fun DictionarySearchBar(
    onSearching: () -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var submittedText by remember { mutableStateOf("") }

    CustomTextField(
        text = text,
        placeholder = "Cari bahan skincare...",
        onValueChange = { newText -> text = newText },
        leadingIcon = painterResource(id = R.drawable.ic_search),
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done,
        isError = false,
        isVisible = false,
        errorMessage = null,
        singleLine = true,
        maxLine = 1,
//        modifier = Modifier
//            .onKeyEvent { keyEvent ->
//                if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Enter) {
//                    submittedText = text
//                    text = ""
//                    onSearching()
//                    true
//                } else {
//                    false
//                }
//            }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryAppBar(title: String, subtitle: String, icon: Painter, onNavigateToFilter: () -> Unit) {
    AppBar(title, subtitle, icon) { onNavigateToFilter() }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowFilter(rating : List<String>?, benefit : List<String>?) {
    FlowRow (horizontalArrangement = Arrangement.Start) {
        rating?.let {
            rating.forEach() { ratingItem ->
                Text(
                    text = ratingItem,
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        benefit?.let {
            benefit.forEach() { benefitItem ->
                Text(
                    text = benefitItem,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}