package com.ayukrisna.skinsift.view.ui.screen.home

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.data.remote.response.article.NewsResultsItem
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onNavigateToNotes: () -> Unit,
    onNavigateToOcr: () -> Unit,
    onNavigateToAssessment: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
) {
    val articleState by viewModel.articleState.collectAsState()
    val context = LocalContext.current

        LaunchedEffect(Unit) {
        viewModel.fetchArticle()
    }

    Surface {
        LazyColumn (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                HomeTopBar()
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                HomeIntro()
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                TitleHome("Pahami Kebutuhanmu")
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                ScannerRow(onNavigateToAssessment, onNavigateToOcr)
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                TitleHome("Bahan Skincare Tersimpan")
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                ScannerHistoryItem(
                     "Cocok Denganmu \uD83D\uDC90",
                    "Catat bahan-bahan yang kamu sukai",
                    onNavigateToNotes
                )
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                ScannerHistoryItem(
                    "Wajib Dihindari ☠",
                    "Catat bahan-bahan yang kamu hindari",
                    onNavigateToNotes
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                TitleHome("Artikel Terkait")
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            when (articleState) {
                is Result.Idle -> item { Text("Idle State") }
                is Result.Loading -> item { LoadingProgress() }
                is Result.Success -> {
                    val articles: List<NewsResultsItem?> = (articleState as Result.Success<List<NewsResultsItem?>>).data
                    if (articles.isNotEmpty()) {
                            items(articles) { article ->
                                article?.let {
                                    LongArticleCard(
                                        title = it.title.orEmpty(),
                                        source = it.source.orEmpty(),
                                        date = it.date.orEmpty(),
                                        snippet = it.snippet.orEmpty(),
                                        thumbnailUrl = it.thumbnail.orEmpty(),
                                        link = it.link.orEmpty()
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        Log.d("Artikel", "$articles")
                    } else {
                        item { Text("Yah, belum artikel di sini!") }
                    }
                }
                is Result.Error -> {
//                    val error = (articleState as Result.Error).error
//                    item { ErrorLayout(error = error) }
                    items(newsResults) { article ->
                        article.let {
                            LongArticleCard(
                                title = it.title.orEmpty(),
                                source = it.source.orEmpty(),
                                date = it.date.orEmpty(),
                                snippet = it.snippet.orEmpty(),
                                thumbnailUrl = it.thumbnail.orEmpty(),
                                link = it.link.orEmpty()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Profile()
    }
}

@Composable
fun Profile(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
//        CircleImageView()
        Text("Hai ✨",
            style = MaterialTheme.typography.titleSmall, modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
        )
    }
}


@Composable
fun HomeIntro(modifier : Modifier = Modifier) {
    Text(
        buildAnnotatedString {
            append(stringResource(R.string.home_intro1))
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(stringResource(R.string.home_intro2))
            }
            append(stringResource(R.string.home_intro3))
        },
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
    )
}

@Composable
fun TitleHome(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
        )
        if (subtitle != null) {
            Text(
                subtitle,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    }
}

@Composable
fun ScannerHistoryItem(
    title: String,
    ingredients: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = ingredients,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ArticleCard(
    articleTitle: String,
    articleDescription: String,
    articlePicture: String? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .width(300.dp)
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
                    text = articleTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = articleDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ScannerRow(assessment: () -> Unit, ocr: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        SkincareScannerCard(
            {ocr()},
            Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        PersonalizedCard(
            {assessment()},
            Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun SkincareScannerCard(assessment: () -> Unit, modifier: Modifier = Modifier) {
    SquareCard(
        bgImage = painterResource(id = R.drawable.card_bg_dark),
        icon = painterResource(id = R.drawable.line_scan_small),
        title = "Pindai Bahan Skincare",
        subtitle = "Pahami komposisi dari skincaremu dengan sekali foto.",
        textColor = MaterialTheme.colorScheme.onPrimary,
        action = {assessment()}
    )
}

@Composable
fun PersonalizedCard(assessment: () -> Unit, modifier: Modifier = Modifier) {
    SquareCard(
        bgImage = painterResource(id = R.drawable.card_bg_light),
        icon = painterResource(id = R.drawable.skincare_icon),
        title = "Personalisasi Skincare",
        subtitle = "Dapatkan rekomendasi produk sesuai dengan kebutuhanmu.",
        textColor = MaterialTheme.colorScheme.onSurface,
        action = {assessment()}
    )
}

@Composable
fun SquareCard(
    action: () -> Unit,
    bgImage: Painter,
    icon: Painter,
    title: String,
    subtitle: String,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .width(178.dp)
            .clickable { action() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = bgImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            ) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(42.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = textColor.copy(alpha = 0.7f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview(modifier: Modifier = Modifier) {
    ArticleCard(
        "Is My Skin Purging or Breaking Out?",
        "Some chalk this up as their skin getting worse before it gets better, but what’s really going on?"
    )
}

@Composable
fun LongArticleCard(
    title: String,
    source: String,
    date: String,
    snippet: String,
    thumbnailUrl: String,
    link: String
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(154.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = "Article Image",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(96.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$source • $date",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = snippet,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLongArticleCard() {
    LongArticleCard(
        title = "Pelembap Anti-Aging Super Ringan yang Sempurna untuk Iklim Tropis",
        source = "Fimela",
        date = "2 jam yang lalu",
        snippet = "Glowell, merek premium skincare baru menghadirkan inovasi produk anti-aging dengan tekstur ringan untuk iklim tropis.",
        thumbnailUrl = "https://serpapi.com/searches/6757bb931d0f30c20756d960/images/97327ba4d99b6fe617bad086ec39b4f82e8b343daf04788559298c901b65e910.jpeg",
        link = "https://www.fimela.com/beauty/read/5826704/pelembap-anti-aging-super-ringan-yang-sempurna-untuk-iklim-tropis"
    )
}

// If ran out of API calls, use this isntead
val newsResults = listOf(
    NewsResultsItem(
        date = "3 hari lalu",
        snippet = "Meski lebih sering mendung, tubuh tetap memerlukan sunscreen saat musim hujan, simak penjelasannya.",
        thumbnail = "https://serpapi.com/searches/675af5b07801ba80cdbfbcac/images/87ca06d65bc80704f5a03785effdb32f415d79dc84562ba3931365cd58af25a5.jpeg",
        link = "https://health.tribunnews.com/2024/12/09/6-tips-jaga-kesehatan-kulit-di-musim-hujan-sunscreen-dan-skincare-berikut-tetap-penting-digunakan",
        position = 1,
        source = "TribunHealth.com",
        title = "6 Tips Jaga Kesehatan Kulit di Musim Hujan, Sunscreen dan Skincare Berikut Tetap Penting Digunakan"
    ),
    NewsResultsItem(
        date = "4 hari lalu",
        snippet = "Cari : Niacinamide, hyaluronic acid, ceramide, dan panthenol. Bahan ini dikenal menenangkan kulit dan membantu menjaga kelembapan.",
        thumbnail = "https://serpapi.com/searches/675af5b07801ba80cdbfbcac/images/87ca06d65bc80704a70e632b4ac19b0b3eee25cd042532c7613dd203a5493a40.jpeg",
        link = "https://www.rri.co.id/lampung/hobi/1175395/tips-memilih-skincare-aman-untuk-kulit-sensitif",
        position = 2,
        source = "RRI.co.id",
        title = "Tips Memilih Skincare Aman untuk Kulit Sensitif"
    ),
    NewsResultsItem(
        date = "2 hari lalu",
        snippet = "Tak selalu harus dengan skincare mahal, dr Zaidul Akbar punya laternatif lain yang bisa buat kulit wajah terlihat sehat dan glowing.",
        thumbnail = "https://serpapi.com/searches/675af5b07801ba80cdbfbcac/images/87ca06d65bc80704aa09c794471cbe70e7ce2a902dcad109309c99b9aef478a0.jpeg",
        link = "https://www.tvonenews.com/lifestyle/kesehatan/276569-rahasia-alami-wajah-glowing-tanpa-skincare-dr-zaidul-akbar-bagikan-tips-mudahnya-cukup-ganti-pola-makan-dengan",
        position = 3,
        source = "TvOneNews",
        title = "Rahasia Alami Wajah Glowing Tanpa Skincare, dr Zaidul Akbar Bagikan Tips Mudahnya, Cukup Ganti Pola Makan dengan..."
    ),
    NewsResultsItem(
        date = "2 minggu lalu",
        snippet = "Pelajari tips skincare lengkap untuk merawat kulit sehat dan bercahaya. Dari rutinitas dasar hingga perawatan khusus, temukan rahasia kulit...",
        thumbnail = "https://serpapi.com/searches/675af5b07801ba80cdbfbcac/images/87ca06d65bc80704a7e921280d59065163b9eec1750aaf2a2199ed680856b911.jpeg",
        link = "https://www.liputan6.com/feeds/read/5796694/tips-skincare-panduan-lengkap-merawat-kulit-sehat-dan-bercahaya",
        position = 4,
        source = "Liputan6.com",
        title = "Tips Skincare: Panduan Lengkap Merawat Kulit Sehat dan Bercahaya"
    ),
    NewsResultsItem(
        date = "2 minggu lalu",
        snippet = "Ketika hujan datang, kelembapan meningkat, dan udara yang lebih lembab bisa menimbulkan berbagai tantangan untuk kulit kita.",
        thumbnail = "https://serpapi.com/searches/675af5b07801ba80cdbfbcac/images/87ca06d65bc80704892b109a8c9baeed4c2cfd5b731c659f70d936fbb8499956.jpeg",
        link = "https://highend-magazine.okezone.com/read/ini-5-tips-skincare-untuk-jaga-kulit-tetap-sehat-dan-bersinar-di-musim-hujan-8P2EU2",
        position = 5,
        source = "Highend Magazine",
        title = "Ini 5 Tips Skincare Untuk Jaga Kulit Tetap Sehat Dan Bersinar Di Musim Hujan!"
    ),
    NewsResultsItem(
        date = "1 minggu lalu",
        snippet = "Bagi Marsha Timothy, menjaga kesehatan kulit tak bisa hanya dengan skincare. Butuh asupan sehat untuk membantu menjaganya dari dalam.",
        thumbnail = "https://serpapi.com/searches/675af5b07801ba80cdbfbcac/images/87ca06d65bc807048e959ff930031adfe4397a2315cd1b70f8c88934a6ae7289.jpeg",
        link = "https://lifestyle.kompas.com/read/2024/12/03/121019820/tips-menjaga-kesehatan-kulit-ala-marsha-timothy-skincare-saja-tak-cukup",
        position = 6,
        source = "Kompas.com",
        title = "Tips Menjaga Kesehatan Kulit ala Marsha Timothy, Skincare Saja Tak Cukup"
    )
)

