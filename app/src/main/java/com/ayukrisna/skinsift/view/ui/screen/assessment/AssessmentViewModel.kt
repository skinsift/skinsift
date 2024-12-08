package com.ayukrisna.skinsift.view.ui.screen.assessment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ayukrisna.skinsift.data.remote.response.product.ProductListItem
import com.ayukrisna.skinsift.domain.usecase.assessment.AssessmentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import com.ayukrisna.skinsift.util.Result
import kotlinx.coroutines.flow.StateFlow

class AssessmentViewModel(
    private val assessmentUseCase: AssessmentUseCase
) : ViewModel() {
    private val _assessmentState = MutableStateFlow<Result<List<ProductListItem>>>(Result.Idle)
    val assessmentState: StateFlow<Result<List<ProductListItem>>> = _assessmentState

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


    fun submitAssessment() {

    }
}