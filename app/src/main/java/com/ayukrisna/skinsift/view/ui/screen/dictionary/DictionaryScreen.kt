package com.ayukrisna.skinsift.view.ui.screen.dictionary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.view.ui.component.AppBar
import com.ayukrisna.skinsift.domain.model.IngredientModel
import com.ayukrisna.skinsift.domain.model.getIngredientDummy
import com.ayukrisna.skinsift.view.ui.component.getRatingColor

@Composable
fun DictionaryScreen(
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
                "Daftar Ingredients",
                "Cari yang kamu butuhkan",
                )
        },
        content = { innerPadding ->
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = paddingValues.calculateBottomPadding())
//                .padding(innerPadding)
            ) {
                LazyColumn {
                    items(dummyDictionaryList) { item ->
                        IngredientsItem(item)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    )
}

@Composable
fun IngredientsItem(item: IngredientModel) {
    val ratingColor = getRatingColor(item.rating)

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
                    text = item.rating,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = ratingColor
                )
//            Box(
//                modifier = Modifier
//                    .wrapContentWidth()
//                    .clip(RoundedCornerShape(30.dp))
//                    .background(ratingColor)
//                    .padding(horizontal = 8.dp)
//            ) {
//                Text(
//                    text = item.rating,
//                    style = MaterialTheme.typography.labelSmall,
//                    color = Color(0xFFFFFFFF)
//                )
//            }
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryAppBar(
    title: String,
    subtitle: String,
    ) {
    AppBar(
        title,
        subtitle,
        )
}

@Preview(showBackground = true)
@Composable
fun DictionaryPreview() {
    SkinSiftTheme {
        DictionaryScreen(PaddingValues(horizontal = 16.dp, vertical = 42.dp))
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewIngredientItem() {
//    SkinSiftTheme {
//        IngredientsItem(
//            ratingColor = Color(0xFF298A4B),
//            rating = "Best",
//            title = "Acai",
//            description = "Pronounced \"ah-sigh-ee\", this small berry has a deep " +
//                    "purple color and is a rich source of antioxidants."
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    SkinSiftTheme {
        DictionaryAppBar("Daftar Ingredients", "Cari yang kamu butuhkan")
    }
}