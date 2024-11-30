package com.ayukrisna.skinsift.view.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.view.ui.component.CircleImageView
import com.ayukrisna.skinsift.view.ui.theme.SkinSiftTheme

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Surface {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            HomeTopBar()
            Spacer(modifier = Modifier.height(12.dp))
            HomeIntro()
            Spacer(modifier = Modifier.height(16.dp))
            ReminderCard()
            Spacer(modifier = Modifier.height(24.dp))
            TitleHome(stringResource(R.string.ingredients_scanner))
            Spacer(modifier = Modifier.height(12.dp))
            ScannerRow()
//            ScannerCard()
            Spacer(modifier = Modifier.height(24.dp))
            TitleHome(stringResource(R.string.ingredients_saved))
            Spacer(modifier = Modifier.height(12.dp))
            ScannerHistoryItem(
                "Cocok Denganmu \uD83D\uDC90",
                "Niacinamide, Retinoid, Hexylresorcinol"
            )
            Spacer(modifier = Modifier.height(12.dp))
            ScannerHistoryItem(
                "Wajib Dihindari ☠",
                "Polyacrylamide, PTFE, Petrolatum"
            )
            Spacer(modifier = Modifier.height(24.dp))
            TitleHome(stringResource(R.string.interesting_articles))
            Spacer(modifier = Modifier.height(6.dp))
            ArticleCardList()
        }
    }
}

@Composable
fun NotificationButton(modifier: Modifier = Modifier) {
    IconButton(
        onClick = { /* Handle button click */ },
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Favorite",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Profile()
        NotificationButton()
    }
}

@Composable
fun Profile(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
//        CircleImageView()
        Text("Hai, Ayu Krisna ✨",
            style = MaterialTheme.typography.titleSmall, modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
        )
    }
}


@Composable
fun HomeIntro(modifier : Modifier = Modifier
) {
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
fun ReminderCard(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.card_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Reminder",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("☀️", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Morning Skincare Routine",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "08.00 - 08.15",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun ScannerCard(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.card_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Image(
                        painter = painterResource(id = R.drawable.scanner),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            "Yuk, Scan Ingredients dari Skincaremu ✨",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(12.dp, 0.dp, 0.dp, 0.dp)
                        )
                        Text(
                            "Biar kamu tau apa saja yang kamu gunakan dalam skincaremu.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(12.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerHistoryItem(
    title: String,
    ingredients: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ArticleCardList(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
    ) {
        ArticleCard(
            "Is My Skin Purging or Breaking Out?",
            "Some chalk this up as their skin getting worse before it gets better, but what’s really going on?"
        )
        ArticleCard(
            "Is My Skin Purging or Breaking Out?",
            "Some chalk this up as their skin getting worse before it gets better, but what’s really going on?"
        )
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

@Preview(showBackground = true)
@Composable
fun ScannerRowPreview() {
    ScannerRow()
}

@Composable
fun ScannerRow(modifier: Modifier = Modifier) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        SkincareScannerCard(
            Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        PersonalizedCard(
            Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SkincareScannerCard(modifier: Modifier = Modifier) {
    SquareCard(
        bgImage = painterResource(id = R.drawable.card_bg_dark),
        icon = painterResource(id = R.drawable.line_scan_small),
        title = "Pindai Bahan Skincare",
        subtitle = "Pahami komposisi dari skincaremu dengan sekali foto.",
        textColor = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun PersonalizedCard(modifier: Modifier = Modifier) {
    SquareCard(
        bgImage = painterResource(id = R.drawable.card_bg_light),
        icon = painterResource(id = R.drawable.skincare_icon),
        title = "Personalisasi Skincare",
        subtitle = "Dapatkan rekomendasi produk sesuai dengan kebutuhanmu.",
        textColor = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun SquareCard(
    bgImage: Painter,
    icon: Painter,
    title: String,
    subtitle: String,
    textColor: Color
) {
    Card(
        modifier = Modifier
            .width(178.dp),
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

/***
 * PREVIEW
 */

@Preview(showBackground = true)
@Composable
fun SkincareScannerCardPreview() {
    SkincareScannerCard()
}

@Preview(showBackground = true)
@Composable
fun PersonalizedCardPreview() {
    PersonalizedCard()
}

@Preview(showBackground = true)
@Composable
fun HomeTopBarPreview() {
    HomeTopBar(modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun NotificationButtonPreview() {
    NotificationButton()
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SkinSiftTheme {
        HomeScreen(paddingValues = PaddingValues(horizontal = 16.dp, vertical = 42.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ReminderCardPreview() {
    ReminderCard()
}

@Preview(showBackground = true)
@Composable
fun ScannerCardPreview() {
    ScannerCard()
}

@Preview(showBackground = true)
@Composable
fun ScannerHistoryItemPreview(modifier: Modifier = Modifier) {
    ScannerHistoryItem(
        "Wajib Dihindari ☠",
        "Polyacrylamide, PTFE, Petrolatum"
    )
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview(modifier: Modifier = Modifier) {
    ArticleCard(
        "Is My Skin Purging or Breaking Out?",
        "Some chalk this up as their skin getting worse before it gets better, but what’s really going on?"
    )
}

@Preview(showBackground = true)
@Composable
fun ArticleCardListPreview(modifier: Modifier = Modifier) {
    ArticleCardList()
}