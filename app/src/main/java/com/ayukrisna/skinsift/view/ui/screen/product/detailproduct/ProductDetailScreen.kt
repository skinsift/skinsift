package com.ayukrisna.skinsift.view.ui.screen.product.detailproduct

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.data.remote.response.DetailProduct
import com.ayukrisna.skinsift.domain.model.ProductDetailModel
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProductDetailScreen (
    id: Int,
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    viewModel: DetailProductViewModel = koinViewModel(),
    modifier : Modifier = Modifier
) {
    val productState by viewModel.productState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchProduct(id)
    }

    Scaffold (
        topBar = {
            ProductDetailAppBar("Detail Skincare", {onBackClick()})
        },

        content = { innerPadding ->
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(
                    top = paddingValues.calculateTopPadding() + 54.dp,
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding())
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (productState) {
                    is Result.Idle -> Text("Idle State")
                    is Result.Loading -> LoadingProgress()
                    is Result.Success -> {
                        val product: DetailProduct = (productState as Result.Success<DetailProduct>).data

                        product.imageUrl?.let { ProductImage(it) }
                            Spacer(modifier = Modifier.height(8.dp))
                            ProductOverview(
                                name = product.productName ?: "Belum ada nama",
                                brand = product.brand ?: "Belum ada merk",
                                type = product.productType ?: "Belum ada tipe",
                                category = product.category ?: "-",
                                skinType = product.skinType ?: "-",
                                bpom = product.bpom ?: "-",
                                description = product.description ?: "Belum ada deskripsi"
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ProductInformation(title = "Kegunaan", information= product.benefit ?: "Belum ada kegunaan")
                            Spacer(modifier = Modifier.height(8.dp))
                            KeyIngredientsInformation(product.keyIngredients ?: "Produk ini tidak memiliki bahan utama")
                            Spacer(modifier = Modifier.height(8.dp))
                            ProductInformation(title = "Bahan dan Komposisi", information= product.ingredients ?: "Belum ada keterangan komposisi.")
                            Spacer(modifier = Modifier.height(8.dp))
                    }
                    is Result.Error -> {
                        val error = (productState as Result.Error).error
                        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    )
}

@Composable
fun ProductImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Skincare Image",
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
        .shadow(
            elevation = 1.dp,
            shape = RoundedCornerShape(16.dp),
            clip = true
        ),
        contentScale = ContentScale.Crop
    )
}


@Composable
fun ProductOverview(name: String, brand: String, type: String, category: String, skinType: String, bpom: String, description: String) {
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
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = brand,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            ProductOverviewItem(painterResource(id = R.drawable.outline_soap), "Jenis Skincare",
                "$type, $category"
            )
            Spacer(modifier = Modifier.height(4.dp))
            ProductOverviewItem(painterResource(id = R.drawable.ic_glowing_skin), "Jenis Kulit", skinType)
            Spacer(modifier = Modifier.height(4.dp))
            ProductOverviewItem(painterResource(id = R.drawable.ic_bpom), "No BPOM", bpom)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
fun ProductOverviewItem(icon: Painter, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Box {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .requiredSize(24.dp).padding(0.dp, 0.dp, 8.dp, 0.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.weight(1.5f)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.weight(3f)
        )
    }
}


@Composable
fun KeyIngredientsInformation(keyIngredients: String) {
    val ingredients = keyIngredients
        .split("\n")
        .mapNotNull { line ->
            val parts = line.split(":")
            if (parts.size == 2) {
                parts[0].trim() to parts[1].trim()
            } else null
        }

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
                text = "Bahan Utama",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (keyIngredients == "Produk ini tidak memiliki bahan utama") {
                Log.d("KeyIngredients", "Nyampe sini, $keyIngredients")
                Text(
                    text = keyIngredients,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
            } else {
                ingredients.forEach { (key, value) ->
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start)
                    {
                        Text(
                            text = key,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.weight(3f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductInformation(
    title: String,
    information: String,
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
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = information,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


@Composable
fun ProductDetailAppBar(title: String, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    CenterAppBar( title = title, onBackClick = {onBackClick()})
}