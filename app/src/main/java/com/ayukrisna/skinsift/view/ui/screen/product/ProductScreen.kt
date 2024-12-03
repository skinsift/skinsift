package com.ayukrisna.skinsift.view.ui.screen.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.domain.model.ProductModel
import com.ayukrisna.skinsift.view.ui.component.AppBar
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductScreen (
    paddingValues: PaddingValues,
    onNavigateToDetail: () -> Unit,
    modifier: Modifier = Modifier
){
    val skincareProducts = listOf(
        ProductModel(
            id = 1,
            name = "Hydrating Face Cream",
            brand = "GlowSkin",
            description = "A deeply hydrating cream that nourishes your skin and provides 24-hour moisture.",
            imageUrl = "https://storage.googleapis.com/skinsift/products/acnes_creamywash.png"
        ),
        ProductModel(
            id = 2,
            name = "Brightening Serum",
            brand = "RadiantCare",
            description = "A serum infused with Vitamin C to brighten your skin and even out skin tone.",
            imageUrl = "https://storage.googleapis.com/skinsift/products/acnes_creamywash.png"
        ),
        ProductModel(
            id = 3,
            name = "Sunscreen SPF 50",
            brand = "SunShield",
            description = "Lightweight, non-greasy sunscreen that protects your skin from harmful UV rays.",
            imageUrl = "https://storage.googleapis.com/skinsift/products/acnes_creamywash.png"
        ),
        ProductModel(
            id = 4,
            name = "Purifying Clay Mask",
            brand = "PureNature",
            description = "A detoxifying mask that removes impurities and minimizes pores.",
            imageUrl = "https://storage.googleapis.com/skinsift/products/acnes_creamywash.png"
        ),
    )

    Scaffold(
        topBar = {
            SkincareAppBar("Kamus Skincare", "Cari yang kamu butuhkan")
        },
        content = { innerPadding ->
            // Padding values should be applied if needed
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {
                ListProducts(skincareProducts,) {onNavigateToDetail()}
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListProducts(skincareProducts: List<ProductModel>, onNavigateToDetail: () -> Unit) {
    FlowColumn(
    ) {
        skincareProducts.forEach() { product ->
            Box(
                modifier = Modifier
                    .padding(2.dp)
            ) {
                SkincareCard(
                    product.name,
                    product.brand,
                    product.description,
                    product.imageUrl,
                    {onNavigateToDetail()}
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkincareAppBar(title: String, subtitle: String) {
    AppBar(title, subtitle)
}

@Composable
fun SkincareCard(
    name: String,
    brand: String,
    description: String,
    imageUrl: String? = null,
    onNavigateToDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .clickable { onNavigateToDetail() }
            .width(180.dp),
//            .padding(0.dp, 0.dp, 8.dp, 8.dp),
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
            AsyncImage(
                model = imageUrl,
                contentDescription = "Skincare Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(4f / 3f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = brand,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SkincareCardPreview(modifier: Modifier = Modifier) {
//    SkincareCard(
//        "Nama Skincare",
//        "Merk Skincare",
//        "Ini deskripsi singkat produknya"
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun ProductScreenPreview() {
//    SkinSiftTheme {
//        ProductScreen()
//    }
//}