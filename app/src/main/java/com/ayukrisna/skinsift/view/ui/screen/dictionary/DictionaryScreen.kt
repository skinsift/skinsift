package com.ayukrisna.skinsift.view.ui.screen.dictionary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.view.ui.component.AppBar
import com.ayukrisna.skinsift.domain.model.IngredientModel
import com.ayukrisna.skinsift.view.ui.component.CustomTextField
import com.ayukrisna.skinsift.view.ui.component.getRatingColor
import androidx.compose.ui.input.key.Key

@Composable
fun DictionaryScreen(
    onNavigateToDetail: () -> Unit,
    onNavigateToFilter: () -> Unit,
    paddingValues: PaddingValues,
    modifier : Modifier = Modifier
) {
    val dummyDictionaryList = listOf(
        IngredientModel(
            id = 1,
            name = "3-O Ethyl Ascorbic Acid",
            rating = "Terbaik",
            description = "A delicious and healthy fruit.",
            benefit = "Rich in fiber and vitamins.",
            category = "Anti Penuaan, Antioksidan, Ekstrak Tumbuhan",
            key = "A1"
        ),
        IngredientModel(
            id = 2,
            name = "Banana",
            rating = "Baik",
            description = "A quick source of energy.",
            benefit = "High in potassium.",
            category = "Anti Penuaan, Antioksidan, Ekstrak Tumbuhan",
            key = "B1"
        ),
        IngredientModel(
            id = 3,
            name = "Carrot",
            rating = "Rata-Rata",
            description = "A crunchy and sweet vegetable.",
            benefit = "Good for eye health.",
            category = "Anti Penuaan, Antioksidan, Ekstrak Tumbuhan",
            key = "C1"
        ),
        IngredientModel(
            id = 4,
            name = "Donut",
            rating = "Buruk",
            description = "A tasty but sugary snack.",
            benefit = "Provides instant energy.",
            category = "Anti Penuaan, Antioksidan, Ekstrak Tumbuhan",
            key = "D1"
        ),
        IngredientModel(
            id = 5,
            name = "Eggplant",
            rating = "Terburuk",
            description = "A versatile vegetable.",
            benefit = "Contains antioxidants.",
            category = "Anti Penuaan, Antioksidan, Ekstrak Tumbuhan",
            key = "E1"
        ),
        IngredientModel(
            id = 6,
            name = "Fig",
            rating = "Terbaik",
            description = "A sweet and nutritious fruit.",
            benefit = "High in calcium and fiber.",
            category = "Anti Penuaan, Antioksidan, Ekstrak Tumbuhan",
            key = "F1"
        ),
        IngredientModel(
            id = 7,
            name = "Grapes",
            rating = "Baik",
            description = "A juicy and delicious fruit.",
            benefit = "Rich in antioxidants.",
            category = "Anti Penuaan, Antioksidan, Ekstrak Tumbuhan",
            key = "G1"
        ),
        IngredientModel(
            id = 8,
            name = "Honey",
            rating = "Rata-Rata",
            description = "A natural sweetener.",
            benefit = "Has antibacterial properties.",
            category = "Sweetener",
            key = "H1"
        ),
        IngredientModel(
            id = 9,
            name = "Ice Cream",
            rating = "Buruk",
            description = "A cold and creamy dessert.",
            benefit = "Tastes great on a hot day.",
            category = "Anti Penuaan, Antioksidan, Ekstrak Tumbuhan",
            key = "I1"
        ),
        IngredientModel(
            id = 10,
            name = "Jackfruit",
            rating = "Terburuk",
            description = "A large and tropical fruit.",
            benefit = "High in vitamin C.",
            category = "Anti Penuaan, Antioksidan, Ekstrak Tumbuhan",
            key = "J1"
        )
    )
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
                LazyColumn {
                    items(dummyDictionaryList) { item ->
                        IngredientsItem(item) { onNavigateToDetail() }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    )
}

@Composable
fun IngredientsItem(
    item: IngredientModel,
    onNavigateToDetail: () -> Unit
) {
    val ratingColor = getRatingColor(item.rating)

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
            Text(
                    text = item.rating,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = ratingColor
                )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
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

//@Preview(showBackground = true)
//@Composable
//fun DictionaryPreview() {
//    SkinSiftTheme {
//        DictionaryScreen(PaddingValues(horizontal = 16.dp, vertical = 42.dp))
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun AppBarPreview() {
//    SkinSiftTheme {
//        DictionaryAppBar("Kamus Ingredients", "Cari yang kamu butuhkan")
//    }
//}