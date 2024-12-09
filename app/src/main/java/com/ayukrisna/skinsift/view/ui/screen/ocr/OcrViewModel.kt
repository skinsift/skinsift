package com.ayukrisna.skinsift.view.ui.screen.ocr

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayukrisna.skinsift.data.remote.response.ml.OcrResponse
import com.ayukrisna.skinsift.domain.usecase.ocr.OcrUseCase
import com.ayukrisna.skinsift.util.FileHelper
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


class OcrViewModel (
    private val ocrUseCase: OcrUseCase,
    private val fileHelper: FileHelper
): ViewModel() {
    private val _ocrState = MutableStateFlow<Result<OcrResponse>>(Result.Idle)
    val ocrState: StateFlow<Result<OcrResponse>> = _ocrState

    private val _selectedUri = MutableStateFlow<Uri?>(null)
    val selectedUri: StateFlow<Uri?> = _selectedUri

    fun setSelectedUri(uri: Uri?) {
        _selectedUri.value = uri
        Log.d("Set URI", "Selected URI: $uri")
    }

    private fun imageUriToMultiPart(photoUri: Uri) : MultipartBody.Part {
        val imageFile = fileHelper.uriToFile(photoUri)
        val requestImageFile = imageFile.asRequestBody("image/png".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        return multipartBody
    }

    fun submitOcr(photoUri: Uri) {
        val multipartBody = imageUriToMultiPart(photoUri)

        viewModelScope.launch {
            _ocrState.value = Result.Loading
            val result = ocrUseCase.execute(multipartBody)
            _ocrState.value = result
        }

    }
}