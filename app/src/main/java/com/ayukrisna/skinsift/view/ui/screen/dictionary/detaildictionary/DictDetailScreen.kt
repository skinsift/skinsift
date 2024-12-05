package com.ayukrisna.skinsift.view.ui.screen.dictionary.detaildictionary

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.data.remote.response.Ingredient
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import org.koin.androidx.compose.koinViewModel
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import com.ayukrisna.skinsift.view.ui.component.getRatingColor

@Composable
fun DictDetailScreen(
    id: Int,
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    viewModel: DictDetailViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val ingredientState by viewModel.ingredientState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchIngredient(id)
    }

    Scaffold(
        topBar = {
            DetailIngredientAppBar(
                title = "Detail Bahan Skincare",
                onBackClick = { onBackClick() }
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(
                    top = paddingValues.calculateTopPadding() + 64.dp,
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding() + 16.dp
                )
                .verticalScroll(rememberScrollState())
            ) {
                when (ingredientState) {
                    is Result.Idle -> Text("Idle State")
                    is Result.Loading -> LoadingProgress()
                    is Result.Success -> {
                        val ingredient: Ingredient = (ingredientState as Result.Success<Ingredient>).data
                        IngredientDetail(ingredient)
                    }
                    is Result.Error -> {
                        val error = (ingredientState as Result.Error).error
                        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    )
}


@Composable
fun IngredientDetail(ingredient: Ingredient) {
    val name = ingredient.nama ?: "Belum ada nama"
    val rating = ingredient.rating ?: "Belum ada rating"
    val ratingColor = getRatingColor(rating)
    val key = ingredient.keyidn ?: "Belum ada deskripsi untuk ini"
    val benefit = ingredient.benefitidn ?: "Belum ada deskripsi untuk ini"
    val category = ingredient.kategoriidn ?: "Belum ada deskripsi untuk ini"
    val description = ingredient.deskripsiidn ?: "Belum ada deskripsi untuk ini"

    Text(text = name,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(16.dp))
    IngredientsOverview(
        ratingColor = ratingColor,
        rating = rating,
        use = benefit,
        category = category
    )
    Spacer(modifier = Modifier.height(12.dp))
    IngredientsDescription(
        keyIngredient = key,
        description = description)
}

@Composable
fun IngredientsOverview(ratingColor: Color, rating: String, use: String, category: String) {
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
                text = stringResource(R.string.more_about_ingre),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            IngredientsOverviewItem(stringResource(R.string.rating), rating, ratingColor)
            Spacer(modifier = Modifier.height(2.dp))
            IngredientsOverviewItem(stringResource(R.string.use), use)
            Spacer(modifier = Modifier.height(2.dp))
            IngredientsOverviewItem(stringResource(R.string.category), category)
        }
    }
}

@Composable
fun IngredientsDescription(
    keyIngredient: String,
    description: String,
    modifier : Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(8.dp),
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
                text = "Key Ingredient",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            keyIngredient.split("|").forEach { item ->
                Text(
                    text = "∙ ${item.trim()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Deskripsi",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
fun IngredientsOverviewItem(title: String, description: String, color : Color = MaterialTheme.colorScheme.onSurface ){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            modifier = Modifier.weight(3f)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DetailIngredientPreview() {
//    SkinSiftTheme {
//        DictDetailScreen(
//
//        )
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailIngredientAppBar(title: String, onBackClick: () -> Unit,) {
    CenterAppBar(
        title = title,
        onBackClick = {onBackClick()})
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewAppBar() {
//    SkinSiftTheme {
//        DetailIngredientAppBar("Detail Ingredients")
//    }
//}


