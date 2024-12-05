package com.ayukrisna.skinsift.view.ui.screen.product.listproduct

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.data.remote.response.ProductListItem
import com.ayukrisna.skinsift.view.ui.component.AppBar
import org.koin.androidx.compose.koinViewModel
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary.SearchBar
import com.ayukrisna.skinsift.view.ui.screen.dictionary.listdictionary.ShowFilter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductScreen (
    skinType: List<String>? = null,
    category: List<String>? = null,
    productViewModel: ProductViewModel = koinViewModel(),
    paddingValues: PaddingValues,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToFilter: () -> Unit,
    modifier: Modifier = Modifier
){
    val productsState by productViewModel.productsState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (skinType == null && category == null) {
            Log.d("ProductFilterViewModel", "Selected filters is empty")
            productViewModel.fetchProducts()
        } else {
            Log.d("ProductFilterViewModel", "Skin type: $skinType, Category: $category")
            productViewModel.searchProducts(skinType = skinType, category =  category)
        }
    }

    Scaffold(
        topBar = {
            SkincareAppBar(
                "Produk Skincare",
                "Cari yang kamu butuhkan",
                painterResource(id = R.drawable.ic_filter)
            ) { onNavigateToFilter() }
        },
        content = { innerPadding ->
            // Padding values should be applied if needed
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding()
                )
            ) {
                Spacer(modifier = Modifier.height(108.dp))
                SearchBar { query ->
                    productViewModel.searchProducts(query, skinType, category)
                }
                if (skinType != null || category != null) {
                    ShowFilter(skinType, category)
                    Spacer(modifier = Modifier.height(24.dp))
                }
                when (productsState) {
                    is Result.Idle -> Text("Idle State")
                    is Result.Loading -> LoadingProgress()
                    is Result.Success -> {
                        val products: List<ProductListItem> = (productsState as Result.Success<List<ProductListItem>>).data
                        if (products.isNotEmpty()) {
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
                        } else {
                            Text("Yah, belum ada produk di sini!")
                        }
                    }
                    is Result.Error -> {
                        val error = (productsState as Result.Error).error
                        Text("Error: $error")
                        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkincareAppBar(title: String, subtitle: String, icon: Painter, onNavigateToFilter: () -> Unit) {
    AppBar(title, subtitle, icon) { onNavigateToFilter() }
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
            .width(180.dp)
            .height(240.dp),
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