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
import androidx.compose.material.icons.Icons

@Composable
fun DictionaryScreen(
    modifier : Modifier = Modifier
) {
    Scaffold(
        topBar = {
            DictionaryAppBar(
                "Daftar Ingredients",
                "Cari yang kamu butuhkan",
                )
        },
        content = { paddingValues ->
            // Padding values should be applied if needed
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                IngredientsItem(Color(0xFF298A4B), "Best", "Acai", "Pronounced \"ah-sigh-ee\", this small berry has a deep purple color and is a rich source of antioxidants.")
                Spacer(modifier = Modifier.height(10.dp))
                IngredientsItem(MaterialTheme.colorScheme.primary, "Good", "Acai", "Pronounced \"ah-sigh-ee\", this small berry has a deep purple color and is a rich source of antioxidants.")
                Spacer(modifier = Modifier.height(10.dp))
                IngredientsItem(Color.Gray, "Common", "Acai", "Pronounced \"ah-sigh-ee\", this small berry has a deep purple color and is a rich source of antioxidants.")
                Spacer(modifier = Modifier.height(10.dp))
                IngredientsItem(MaterialTheme.colorScheme.error, "Worst", "Acai", "Pronounced \"ah-sigh-ee\", this small berry has a deep purple color and is a rich source of antioxidants.")
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    )
}

@Composable
fun IngredientsItem(ratingColor: Color, rating: String, title: String, description: String) {
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
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .background(ratingColor)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = rating,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFFFFFFF) // Green color for the "Best" tag
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
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
        DictionaryScreen( )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIngredientItem() {
    SkinSiftTheme {
        IngredientsItem(
            ratingColor = Color(0xFF298A4B),
            rating = "Best",
            title = "Acai",
            description = "Pronounced \"ah-sigh-ee\", this small berry has a deep " +
                    "purple color and is a rich source of antioxidants."
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    SkinSiftTheme {
        DictionaryAppBar("Daftar Ingredients", "Cari yang kamu butuhkan")
    }
}