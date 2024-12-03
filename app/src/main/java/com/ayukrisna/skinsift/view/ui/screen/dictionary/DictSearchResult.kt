package com.ayukrisna.skinsift.view.ui.screen.dictionary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.domain.model.IngredientModel
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar

@Composable
fun DictSearchResult(
    onNavigateToDetail: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onBackClick: () -> Unit,
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
            DictSearchAppBar (
                title = "Hasil Pencarian",
                onBackClick = { onBackClick() }
            )
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
                DictionarySearchBar { onNavigateToSearch() }
//                Spacer(modifier = Modifier.height(32.dp))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictSearchAppBar(title: String, onBackClick: () -> Unit,) {
    CenterAppBar(
        title = title,
        onBackClick = {onBackClick()})
}

//@Preview(showBackground = true)
//@Composable
//fun DictionaryPreview() {
//    SkinSiftTheme {
//        DictionaryScreen(PaddingValues(horizontal = 16.dp, vertical = 42.dp))
//    }
//}
