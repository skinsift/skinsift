package com.ayukrisna.skinsift.view.ui.screen.ocr

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.data.remote.response.ml.MatchedIngredientsItem
import com.ayukrisna.skinsift.data.remote.response.ml.OcrResponse
import com.ayukrisna.skinsift.data.remote.response.ml.WarningsItem
import com.ayukrisna.skinsift.util.CameraHelper
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import com.ayukrisna.skinsift.view.ui.component.getRatingColor
import com.ayukrisna.skinsift.view.ui.screen.assessment.PhotoModalBottomSheet
import org.koin.androidx.compose.koinViewModel
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.ErrorLayout
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress
import com.ayukrisna.skinsift.view.ui.screen.home.TitleHome
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView

@Composable
fun OcrScreen (
    paddingValues: PaddingValues,
    onNavigateToDetail: (Int) -> Unit,
    onBackClick: () -> Unit,
    viewModel: OcrViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    /**
     * Camera and Gallery
     */
    val context = LocalContext.current
    val authority = stringResource(id = R.string.fileprovider)
//    val cameraHelper = remember { CameraHelper(context, authority) }

    val selectedUri by viewModel.selectedUri.collectAsState()
    val ocrState by viewModel.ocrState.collectAsState()
//    val tempUri = rememberSaveable { mutableStateOf<Uri?>(null) }

    LaunchedEffect(selectedUri) {
        selectedUri?.let { uri ->
            viewModel.submitOcr(uri)
        }
    }

    val onSetUri: (Uri) -> Unit = { newUri ->
        viewModel.setSelectedUri(newUri)
    }

//    val imagePicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//        onResult = { uri ->
//            uri?.let(onSetUri)
//        }
//    )
//
//    val takePhotoLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicture(),
//        onResult = { isSaved -> if (isSaved) tempUri.value?.let(onSetUri) }
//    )
//
//    val cameraPermissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            tempUri.value = cameraHelper.getTempUri()
//            tempUri.value?.let { takePhotoLauncher.launch(it) }
//        } else {
//            Toast.makeText(context, "Camera permission is required to take photos.", Toast.LENGTH_LONG).show()
//        }
//    }


    /**
     * testing android image cropper
     */
    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            if (result.isSuccessful) {
                val croppedUri = result.uriContent
                if (croppedUri != null) {
                    onSetUri(croppedUri)
                }
            } else {
                Log.e("ImageCrop", "Cropping failed: ${result.error}")
            }
        }
    }

//    var showBottomSheet by remember { mutableStateOf(false) }
//
//    if (showBottomSheet) {
//        PhotoModalBottomSheet(
//            onDismiss = { showBottomSheet = false },
//            onTakePhotoClick = {
//                showBottomSheet = false
//                cameraHelper.requestCameraPermission(cameraPermissionLauncher) {
//                    tempUri.value = cameraHelper.getTempUri()
//                    tempUri.value?.let { takePhotoLauncher.launch(it) }
//                }
//            },
//            onPhotoGalleryClick = {
//                showBottomSheet = false
////                imagePicker.launch(
////                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//                val cropOption = CropImageContractOptions(uri = null, CropImageOptions())
//                imageCropLauncher.launch(cropOption)
//            },
//        )
//    }

    Scaffold(
        topBar = {
            OcrAppBar(
                title = "Pindai Bahan Skincare",
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
                if (selectedUri != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = selectedUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                AddImageOcr(
                    text = "Scan Komposisi Skincare",
                    onClick = {
                        val cropOption = CropImageContractOptions(uri = null, CropImageOptions())
                        imageCropLauncher.launch(cropOption)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                when (ocrState) {
                    is Result.Loading -> {
                        LoadingProgress()
                    }

                    is Result.Success -> {
                        TitleHome("Hasil Pindai Bahan Skincare")
                        Spacer(Modifier.height(12.dp))
                        val response = (ocrState as Result.Success<OcrResponse>).data

                        response.warnings?.forEach { warning ->
                            warning?.let { WarningPreference(warningItem = it) }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        response.matchedIngredients?.forEach { matchedIngredient ->
                            matchedIngredient?.let {
                                MatchingIngredientsItem(
                                    item = it,
                                    onNavigateToDetail = {
                                        it.id?.let { it1 -> onNavigateToDetail(it1) }
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    is Result.Error -> {
                        val error = (ocrState as Result.Error).error
                        ErrorLayout(error = error)
                    }

                    is Result.Idle -> {
                        if (selectedUri == null) {
                            NoOcr()
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun OcrAppBar(title: String, onBackClick: () -> Unit,) {
    CenterAppBar(
        title = title,
        onBackClick = {onBackClick()})
}

@Composable
fun WarningPreference(
    warningItem: WarningsItem,
) {
    var isVisible by remember { mutableStateOf(true) }

    if (isVisible) {
        val isSuitable = warningItem.category == "Bahan Cocok"
        val cardColor = if (isSuitable) Color(0xFFD8FFE6) else Color(0xFFFFDAD6)
        val textColor = if (isSuitable) Color(0xFF298A4B) else Color(0xFF840F0F)
        val prefix = if (isSuitable) "✅ Notes" else "‼️ Notes"
        val combinedDetails = warningItem.details?.filterNotNull()?.joinToString(", ") ?: ""

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = cardColor
            ),
            elevation = CardDefaults.cardElevation(1.dp),
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = { isVisible = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(y = (-12).dp, x = (+6).dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = textColor
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = prefix,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val text = if (isSuitable) {
                        "Yay! Kami menemukan bahan yang cocok untukmu."
                    } else {
                        "Perhatian! Ada bahan yang berbahaya untukmu."
                    }

                    Text(
                        text = buildAnnotatedString {
                            append("$text \nBahan: ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(combinedDetails)
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
fun MatchingIngredientsItem(
    item: MatchedIngredientsItem,
    onNavigateToDetail: () -> Unit
) {
    val ratingColor = item.rating?.let { getRatingColor(it) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToDetail() },
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
            item.rating?.let {
                if (ratingColor != null) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = ratingColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            item.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            item.category?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun AddImageOcr(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun NoOcr() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.skin_care_2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp).graphicsLayer(alpha = 0.5f)
            )
            Text(
                text = "Hasil scan bahan skincaremu akan tampil di sini!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        }
    }
}