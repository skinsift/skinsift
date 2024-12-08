package com.ayukrisna.skinsift.view.ui.screen.assessment

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.view.ui.component.AssessmentNavButton
import com.ayukrisna.skinsift.view.ui.component.AssessmentQuestion
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.ayukrisna.skinsift.view.ui.component.AssessmentSelector
import org.koin.androidx.compose.koinViewModel

@Composable
fun AssessmentScreen (
    paddingValues: PaddingValues,
    viewModel: AssessmentViewModel = koinViewModel(),
    onDoneClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    /***
     * Assessment options
     */
    val sensitiveItems = viewModel.sensitiveItems
    val sensitiveOptions= viewModel.sensitiveOptions

    val tujuanItems = viewModel.tujuanItems
    val tujuanLetters = viewModel.tujuanOptions

    val fungsiItems = viewModel.fungsiItems
    val fungsiLetters = viewModel.fungsiOptions

    val hamilMenyusuiItems = viewModel.hamilMenyusuiItems
    val hamilMenyusuiLetters = viewModel.hamilMenyusuiOptions


    Scaffold(
    topBar = {
        AssessmentAppBar(
            title = "Asesmen Personalisasi",
            onBackClick = { onBackClick() }
        )
    },
    content = { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    top = paddingValues.calculateTopPadding() + 72.dp,
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState()),
        ) {
            /***
             * PERTANYAAN 1
             */
//            AssessmentQuestion("1. Apa tipe kulitmu?")
//            Spacer(modifier = Modifier.height(12.dp))
//            ScannerCard("Deteksi Tipe Kulit", "Kurang yakin dengan tipe kulitmu? Deteksi tipe kulit dengan mengupload foto wajahmu atau lakukan selfie pada kamera.")
//            Spacer(modifier = Modifier.height(12.dp))
//            ScannerCard("Hasil Scan: Kulit Kering", "Lakukan scan kembali", painterResource(id = R.drawable.skin_dummy))
//            Spacer(modifier = Modifier.height(12.dp))

            /***
             * PERTANYAAN 2
             */
            AssessmentQuestion("2. Apakah kulit kamu sensitif atau sering bereaksi terhadap produk tertentu?")
            Spacer(modifier = Modifier.height(12.dp))
            AssessmentSelector(
                items = sensitiveItems,
                options = sensitiveOptions,
                selectedItemFlow = viewModel.selectedSensitive,
                onSelectOption = { index -> viewModel.setSensitive(index) },
            )
            Spacer(modifier = Modifier.height(24.dp))

            /***
             * PERTANYAAN 3
             */
            AssessmentQuestion("3. Apa tujuan utama kamu menggunakan produk ini?")
            Spacer(modifier = Modifier.height(12.dp))
            AssessmentSelector(
                items = tujuanItems,
                options = tujuanLetters,
                selectedItemFlow = viewModel.selectedTujuan,
                onSelectOption = { index -> viewModel.setTujuan(index) },
            )
            Spacer(modifier = Modifier.height(24.dp))

            /***
             * PERTANYAAN 4
             */
            AssessmentQuestion("4. Apakah kamu mencari produk dengan fungsi tambahan?")
            Spacer(modifier = Modifier.height(12.dp))
            AssessmentSelector(
                items = fungsiItems,
                options = fungsiLetters,
                selectedItemFlow = viewModel.selectedFungsi,
                onSelectOption = { index -> viewModel.setFungsi(index) },
            )
            Spacer(modifier = Modifier.height(24.dp))

            /***
             * PERTANYAAN 5
             */
            AssessmentQuestion("5. Apakah kamu sedang hamil atau menyusui?")
            Spacer(modifier = Modifier.height(12.dp))
            AssessmentSelector(
                items = hamilMenyusuiItems,
                options = hamilMenyusuiLetters,
                selectedItemFlow = viewModel.selectedHamilMenyusui,
                onSelectOption = { index -> viewModel.setHamilMenyusui(index) },
            )
            Spacer(modifier = Modifier.height(24.dp))

            AssessmentNavButton({onDoneClick()}, {onBackClick()} )
        }
    },
        )
}

@Composable
fun ScannerCard(
    title: String,
    subtitle: String,
    image: Painter? = null,
    modifier: Modifier = Modifier
) {
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
                painter = painterResource(id = R.drawable.card_bg_dark),
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
                    if (image != null) {
                        Image(
                            painter = image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(120.dp)
                                .width(80.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        modifier = Modifier
                            .weight(3f),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        )
                    }
                    Icon(
                        modifier = Modifier.weight(1f),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}