package com.ayukrisna.skinsift.view.ui.screen.dictionary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme

@Composable
fun DetailIngredientScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            DetailIngredientAppBar("Detail Ingredients")
        },
        content = { paddingValues->
            // Padding values should be applied if needed
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
            ) {
                Text(text = "Acetyl Glucosamine",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                IngredientsOverview(
                    ratingColor = Color(0xFF298A4B),
                    rating = "Best",
                    use = "Hydration, Soothing, Evens Skin Tone",
                    category = "Antioxidant, Humectant"
                )
                Spacer(modifier = Modifier.height(12.dp))
                IngredientsDescription(
                    description =
                        "Asam amino gula yang dapat ditemukan di kulit dan memiliki manfaat penting. Salah satunya adalah sebagai prekursor untuk biosintesis pelembap super, asam hialuronat. Jadi, acetyl glucosamine sendiri juga merupakan bahan penting yang identik dengan kulit dan merupakan faktor pelembap alami.\n" +
                                "\n" +
                                "Namun, itu bukan satu-satunya manfaatnya. Acetyl glucosamine memiliki dua manfaat lain yang terbukti melalui uji klinis double-blind. Pertama, ini adalah bahan yang menjanjikan untuk melawan keriput: penggunaan 2% dapat memperbaiki keriput, terutama di area sekitar mata."
                )
            }
        }
    )
}

@Composable
fun IngredientsOverview(ratingColor: Color, rating: String, use: String, category: String) {
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
                text = "Dekripsi",
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
            fontWeight = FontWeight.Medium,
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

@Preview(showBackground = true)
@Composable
fun DetailIngredientPreview() {
    SkinSiftTheme {
        DetailIngredientScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailIngredientAppBar(title: String) {
    CenterAppBar(title)
}

@Preview(showBackground = true)
@Composable
fun PreviewAppBar() {
    SkinSiftTheme {
        DetailIngredientAppBar("Detail Ingredients")
    }
}


