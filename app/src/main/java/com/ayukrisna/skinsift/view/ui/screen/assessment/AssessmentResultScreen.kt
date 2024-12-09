package com.ayukrisna.skinsift.view.ui.screen.assessment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.data.remote.response.product.ProductListItem
import com.ayukrisna.skinsift.domain.model.ProductModel
import com.ayukrisna.skinsift.view.ui.screen.product.listproduct.SkincareCard
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme

@Composable
fun AssessmentResultScreen(
    paddingValues: PaddingValues,
    isHamil: Boolean,
    products: List<ProductListItem>,
    onNavigateToDetail: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AssessmentAppBar(
                title = "Hasil Personalisasi",
                onBackClick = { onBackClick() }
            )
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        top = paddingValues.calculateTopPadding() + 72.dp,
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        bottom = innerPadding.calculateBottomPadding()
                    ),
            ) {
                if (isHamil) {
                    PregnancyWarning()
                }
                LazyVerticalGrid (
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(4.dp, 8.dp, 4.dp, 8.dp)
                ) {
                    items(products) { product ->
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                        ) {
                            SkincareCard(
                                product.productName ?: "Belum ada nama",
                                product.brand ?: "Belum ada brand",
                                product.description ?: "Belum ada deskripsi",
                                product.imageUrl,
                                {onNavigateToDetail(product.id)}

                            )
                        }
                    }
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun PregnancyWarningPreview() {
    SkinSiftTheme {
        PregnancyWarning()
    }
}


@Composable
fun PregnancyWarning() {
    var isVisible by remember { mutableStateOf(true) }

    if (isVisible) {
        Card(
            modifier = Modifier
//                .padding(16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceBright
            ),
            elevation = CardDefaults.cardElevation(1.dp),
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // Close Icon Button positioned slightly higher
                IconButton(
                    onClick = { isVisible = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(y = (-12).dp, x = (+6).dp) // Moves the button up
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Content (Text) centered
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Peringatan!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Produk ini aman digunakan selama kehamilan, tetapi harus digunakan sesuai dengan rekomendasi dokter.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
