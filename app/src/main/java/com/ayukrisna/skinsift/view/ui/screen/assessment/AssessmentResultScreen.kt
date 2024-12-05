package com.ayukrisna.skinsift.view.ui.screen.assessment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.domain.model.ProductModel

@Composable
fun AssessmentResultScreen(
    paddingValues: PaddingValues,
    onNavigateToDetail: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
//                ListProducts(skincareProducts,) {onNavigateToDetail()}
            }
        },
    )
}