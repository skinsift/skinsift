package com.ayukrisna.skinsift.view.ui.screen.assessment

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.ml.AssessmentResponse
import com.ayukrisna.skinsift.domain.usecase.assessment.AssessmentUseCase
import com.ayukrisna.skinsift.util.FileHelper
import kotlinx.coroutines.flow.MutableStateFlow
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class AssessmentViewModel(
    private val assessmentUseCase: AssessmentUseCase,
    private val fileHelper: FileHelper
) : ViewModel() {
    private val _assessmentState = MutableStateFlow<Result<AssessmentResponse>>(Result.Idle)
    val assessmentState: StateFlow<Result<AssessmentResponse>> = _assessmentState

    /**
     * Questions: 1. Skin Type
     */
    private val _selectedUri = MutableStateFlow<Uri?>(null)
    val selectedUri: StateFlow<Uri?> = _selectedUri

    fun setSelectedUri(uri: Uri?) {
        _selectedUri.value = uri
        Log.d("Set URI", "Selected URI: $uri")
    }

    private fun imageUriToMultiPart(photoUri: Uri) : MultipartBody.Part {
        val imageFile = fileHelper.uriToFile(photoUri)
//        val reducedImageFile = fileHelper.reduceFileImage(imageFile)
        val requestImageFile = imageFile.asRequestBody("image/png".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        return multipartBody
    }

    /**
     * Questions: 2. Sensitive
     */
    val sensitiveItems: List<String> = listOf(
        "Iya",
        "Tidak",
    )

    val sensitiveOptions = listOf("a", "b")

    private val _selectedSensitive = MutableStateFlow<String?>(null)
    val selectedSensitive: StateFlow<String?> = _selectedSensitive

    fun setSensitive(index: Int) {
        _selectedSensitive.value = sensitiveOptions.getOrNull(index)
        Log.d("Set Sensitive", "set sensitive: ${_selectedSensitive.value}")
    }

    /**
     * Questions: 3. Tujuan
     */

    val tujuanItems: List<String> = listOf(
        "Membersihkan wajah dari kotoran, minyak, sisa makeup, dan debu",
        "Mengatasi masalah kulit seperti jerawat, noda hitam, kulit kusam, atau tanda penuaan",
        "Menghidrasi kulit, pembersihan mendalam, dan menenangkan kulit",
        "Menjaga kelembapan kulit, mencegah kulit kering, dan mengurangi resiko iritasi atau kerusakan kulit"
    )

    val tujuanOptions = listOf("a", "b", "c", "d")

    private val _selectedTujuan = MutableStateFlow<String?>(null)
    val selectedTujuan: StateFlow<String?> = _selectedTujuan

    fun setTujuan(index: Int) {
        _selectedTujuan.value = tujuanOptions.getOrNull(index)
        Log.d("Set Tujuan", "set tujuan: ${_selectedTujuan.value}")
    }

    /**
     * Questions: 4. Fungsi Tambahan
     */
    val fungsiItems: List<String> = listOf(
        "Anti-aging",
        "Melembapkan kulit",
        "Menutrisi kulit",
        "Mencerahkan kulit",
        "Menyegarkan kulit",
        "Menenangkan kulit",
        "Tidak, hanya fungsi utama",
    )

    val fungsiOptions = listOf("a", "b", "c", "d", "e", "f", "g")

    private val _selectedFungsi = MutableStateFlow<String?>(null)
    val selectedFungsi: StateFlow<String?> = _selectedFungsi

    fun setFungsi(index: Int) {
        _selectedFungsi.value = fungsiOptions.getOrNull(index)
        Log.d("Set fungsi", "set fungsi: ${_selectedFungsi.value}")
    }

    /**
     * Questions: 4. Hamil dan Menyusui
     */
    val hamilMenyusuiItems: List<String> = listOf(
        "Iya",
        "Tidak",
    )

    val hamilMenyusuiOptions = listOf("a", "b")

    private val _selectedHamilMenyusui = MutableStateFlow<String?>(null)
    val selectedHamilMenyusui: StateFlow<String?> = _selectedHamilMenyusui

    fun setHamilMenyusui(index: Int) {
        _selectedHamilMenyusui.value = hamilMenyusuiOptions.getOrNull(index)
        Log.d("Set hamil menyusui", "set hamil menyusui: ${_selectedHamilMenyusui.value}")
    }


    fun validateAndSubmit(photoUri: Uri?, sensitive: String?, tujuan: String?, fungsi: String?, hamilMenyusui: String?) {
        if (photoUri == null || sensitive == null || tujuan == null || fungsi == null || hamilMenyusui == null) {
            _assessmentState.value = Result.Error("All fields are required.")
            return
        }
        submitAssessment(photoUri, sensitive, tujuan, fungsi, hamilMenyusui)
    }

    private fun submitAssessment(photoUri: Uri, sensitive: String, tujuan: String, fungsi: String, hamilMenyusui: String) {
        val multipartBody = imageUriToMultiPart(photoUri)

        viewModelScope.launch {
            _assessmentState.value = Result.Loading
            val result = assessmentUseCase.execute(multipartBody, sensitive,tujuan, fungsi, hamilMenyusui)
            _assessmentState.value = result
        }

    }
}