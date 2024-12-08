package com.ayukrisna.skinsift.view.ui.screen.assessment

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.ayukrisna.skinsift.data.remote.response.ingredients.Filter
import com.ayukrisna.skinsift.data.remote.response.ml.AssessmentResponse
import com.ayukrisna.skinsift.data.remote.response.product.ProductListItem
import com.ayukrisna.skinsift.util.CameraHelper
import com.ayukrisna.skinsift.view.ui.component.AssessmentSelector
import org.koin.androidx.compose.koinViewModel
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress

@Composable
fun AssessmentScreen (
    paddingValues: PaddingValues,
    viewModel: AssessmentViewModel = koinViewModel(),
    onDoneClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    /***
     * Assessment Variables
     */
    val assessmentState by viewModel.assessmentState.collectAsState()

    val sensitiveItems = viewModel.sensitiveItems
    val sensitiveOptions= viewModel.sensitiveOptions
    val selectedSensitive by viewModel.selectedSensitive.collectAsState()

    val tujuanItems = viewModel.tujuanItems
    val tujuanLetters = viewModel.tujuanOptions
    val selectedTujuan by viewModel.selectedTujuan.collectAsState()

    val fungsiItems = viewModel.fungsiItems
    val fungsiLetters = viewModel.fungsiOptions
    val selectedFungsi by viewModel.selectedFungsi.collectAsState()

    val hamilMenyusuiItems = viewModel.hamilMenyusuiItems
    val hamilMenyusuiLetters = viewModel.hamilMenyusuiOptions
    val selectedHamilMenyusui by viewModel.selectedHamilMenyusui.collectAsState()


    /**
     * Camera and Gallery
     */
    val context = LocalContext.current
    val authority = stringResource(id = R.string.fileprovider)
    val cameraHelper = remember { CameraHelper(context, authority) }

    val selectedUri by viewModel.selectedUri.collectAsState()
    val tempUri = rememberSaveable { mutableStateOf<Uri?>(null) }

    val onSetUri: (Uri) -> Unit = { newUri ->
        viewModel.setSelectedUri(newUri)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> uri?.let(onSetUri) }
    )

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved -> if (isSaved) tempUri.value?.let(onSetUri) }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            tempUri.value = cameraHelper.getTempUri()
            tempUri.value?.let { takePhotoLauncher.launch(it) }
        } else {
            Toast.makeText(context, "Camera permission is required to take photos.", Toast.LENGTH_LONG).show()
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        PhotoModalBottomSheet(
            onDismiss = { showBottomSheet = false },
            onTakePhotoClick = {
                showBottomSheet = false
                cameraHelper.requestCameraPermission(cameraPermissionLauncher) {
                    tempUri.value = cameraHelper.getTempUri()
                    tempUri.value?.let { takePhotoLauncher.launch(it) }
                }
            },
            onPhotoGalleryClick = {
                showBottomSheet = false
                imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
        )
    }

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
            AssessmentQuestion("1. Apa tipe kulitmu?")
            Spacer(modifier = Modifier.height(12.dp))
            if (selectedUri == null) {
                ScannerCard(
                    title = "Deteksi Tipe Kulit",
                    subtitle = "Kurang yakin dengan tipe kulitmu? Deteksi tipe kulit dengan mengupload foto wajahmu atau lakukan selfie pada kamera.",
                    onClick = { showBottomSheet = true },)
            } else {
                ScannerCard(
                    title = "Foto berhasil ditambahkan.",
                    subtitle = "Lakukan scan kembali",
                    selectedUri = selectedUri,
                    onClick = { showBottomSheet = true },)
            }
            Spacer(modifier = Modifier.height(24.dp))

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

            val isSubmitEnabled = selectedUri != null && selectedSensitive != null && selectedTujuan != null && selectedFungsi != null && selectedHamilMenyusui != null
            if (!isSubmitEnabled) {
                Log.d("whyyyy", "selectedUri: $selectedUri\n selectedsensitive: $selectedSensitive\n selectedtujuan: $selectedTujuan\n selectedfungsi: $selectedFungsi\n selectedHamilMenyusui: $selectedHamilMenyusui")
            }
            AssessmentNavButton(
                onClick = {
                    viewModel.validateAndSubmit(selectedUri, selectedSensitive, selectedTujuan, selectedFungsi, selectedHamilMenyusui)
                },
                onBackClick = {
                    onBackClick()
                },
                isEnabled = isSubmitEnabled
            )
        }
        when (assessmentState) {
            is Result.Idle -> {}
            is Result.Loading -> LoadingProgress()
            is Result.Success -> {
                var showSuccessDialog by remember { mutableStateOf(true) }
                val assessmentResult: AssessmentResponse = (assessmentState as Result.Success<AssessmentResponse>).data

                Log.d("Assessment Result", "Assessment Result: $assessmentResult")

                if (showSuccessDialog) {
                    AlertDialog(
                        onDismissRequest = { showSuccessDialog = false },
                        title = { Text(text = "Berhasil!") },
                        text = { Text(text = "Assessmen berhasil! Yuk, lihat produk yang sesuai dengan kebutuhanmu.")},
                        confirmButton = {
                            Button(onClick = {
                                showSuccessDialog = false
//                                onDoneClick()
                            }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
            is Result.Error -> {
                val error = (assessmentState as Result.Error).error
                var showErrorDialog by remember { mutableStateOf(true) }

                if (showErrorDialog) {
                    AlertDialog(
                        onDismissRequest = { showErrorDialog = false },
                        title = { Text(text = "Ada yang salah...") },
                        text = { Text(text = "Mohon maaf. Terdapat eror: $error")},
                        confirmButton = {
                            Button(onClick = {
                                showErrorDialog = false
                                onBackClick()
                            }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    },
        )
}

@Composable
fun ScannerCard(
    title: String,
    subtitle: String,
    selectedUri: Uri? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                    if (selectedUri != null) {
                        AsyncImage(
                            model = selectedUri,
                            contentDescription = "Selected Skin Image",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoModalBottomSheet(
    onDismiss: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onPhotoGalleryClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Ambil Gambar", modifier = Modifier.padding(bottom = 4.dp))

            Button(
                onClick = onTakePhotoClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
            ) {
                Text(text = "Ambil dari Kamera")
            }

            Button(
                onClick = onPhotoGalleryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
            ) {
                Text(text = "Ambil dari Galeri")
            }

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
            ) {
                Text(text = "Cancel")
            }
        }
    }
}