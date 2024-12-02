package com.ayukrisna.skinsift.view.ui.screen.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.domain.model.ProductDetailModel
import com.ayukrisna.skinsift.view.ui.component.AppBar
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar


@Composable
fun ProductDetailScreen (
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    modifier : Modifier = Modifier
) {

    val dummyDetail = ProductDetailModel(
        id = 3,
        name = "Marigold Clearings Petal Toner",
        type = "Cleanser",
        category = "Toner",
        skinType = "Kering",
        brand = "NPURE",
        keyIngredients = "Anti-acne: Niacinamide\nCell-communicating ingredient: Niacinamide\nExfoliant: Glycolic Acid\nSkin brightening: Licorice Glycyrrhiza Glabra Extract, Niacinamide\nSkin-identical ingredient: Glycerin\nSoothing: Licorice Glycyrrhiza Glabra Extract, Allantoin",
        ingredients = "Aqua, Glycerin, Lactic Acid, Tromethamine, Propylene Glycol, Niacinamide, Butylene Glycol, Fructooligosaccharides, Beta Vulgaris Root Extract, Allantoin, Aloe Barbadensis Leaf Extract, Maltodextrin, Calcium Pantothenate, Urea, Caprylyl Glycol, Magnesium Lactate, Potassium Lactate, Papain, Ethylhexylglycerin, Panthenol, Biotin, Folic Acid, Ascorbic Acid, Tocopherol, Retinyl Palmitate, Pyridoxine HCl, Arachis Hypogaea Oil, Calendula Officinalis Flower Extract, Proline, Alanine, Serine, Phenoxyethanol, PEG-40 Hydrogenated Castor Oil, Disodium EDTA, Calendula Officinalis Flower, Xanthan Gum, Sodium Citrate, Hydroxypropyl Cyclodextrin, Citric Acid, Cyanocobalamin, 7-Dehydrocholesterol, Lecithin, Acetyl Glutamine, sh-oligopeptide-1, sh-oligopeptide-2, sh-polypeptide-1, sh-polypeptide-9, sh-polypeptide-11, Bacillus/Soybean/Folic Acid Ferment Extract, Sodium Hyaluronate, 1,2-hexanediol, Fragrance (Parfum) Components and Finished Fragrances.",
        description = "Toner dengan kelopak bunga Marigold asli yang dan mengandung Papain sebagai Natural Enzymatic Exfoliator untuk mengangkat sel kulit mati dan membantu proses regenerasi kulit.",
        benefit = "Menutrisi kulit, mencegah penuaan dini, melindungi kulit dari radikal bebas, menghaluskan, dan mencerahkan kulit.",
                bpom = "NA18221202239",
        imageUrl = "https://storage.googleapis.com/skinsift/products/npure_marigoldclearingspetaltoner.jpg"
    )

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
                .verticalScroll(rememberScrollState())
            ) {
                ProductDetailContent(dummyDetail) {
                    ProductImage(it.imageUrl)
                    Spacer(modifier = Modifier.height(8.dp))
                    ProductOverview(
                        name = it.name,
                        brand = it.brand,
                        type = it.type,
                        category = it.category,
                        skinType = it.skinType,
                        bpom = it.bpom,
                        description = it.description
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ProductInformation(title = "Kegunaan", information= it.benefit)
                    Spacer(modifier = Modifier.height(8.dp))
                    KeyIngredientsInformation(it.keyIngredients)
                    Spacer(modifier = Modifier.height(8.dp))
//                    ProductInformation(title = "Deskripsi", information= it.description)
//                    Spacer(modifier = Modifier.height(8.dp))
                    ProductInformation(title = "Bahan dan Komposisi", information= it.ingredients)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    )
}

@Composable
fun ProductDetailContent(
    dummyDetail: ProductDetailModel,
    content: @Composable (ProductDetailModel) -> Unit
) {
    Column {
        content(dummyDetail)
    }
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
        Box() {
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
                text = "Bahan Utama",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
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