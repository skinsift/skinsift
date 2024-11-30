package com.ayukrisna.skinsift.view.ui.screen.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.view.ui.component.AppBar
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme

@Composable
fun ProductScreen (modifier: Modifier = Modifier){
    Scaffold(
        topBar = {
            SkincareAppBar("Daftar Skincare", "Cari yang kamu butuhkan")
        },
        content = { paddingValues->
            // Padding values should be applied if needed
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
            ) {

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkincareAppBar(title: String, subtitle: String) {
    AppBar(title, subtitle)
}

@Composable
fun SkincareCard(
    skincareTitle: String,
    skincareBrand: String,
    skincareDescription: String,
    skincarePicture: String? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .width(156.dp)
            .padding(0.dp, 0.dp, 16.dp, 0.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.skincare),
                contentDescription = "Skin care products",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(4f / 3f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = skincareTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = skincareBrand,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = skincareDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SkincareCardPreview(modifier: Modifier = Modifier) {
    SkincareCard(
        "Nama Skincare",
        "Merk Skincare",
        "Ini deskripsi singkat produknya"
    )
}

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    SkinSiftTheme {
        ProductScreen()
    }
}