package com.ayukrisna.skinsift.view.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getRatingColor(rating: String): Color {

    return when (rating) {
        "Terbaik" -> Color(0xFF298A4B)
        "Baik" -> MaterialTheme.colorScheme.primary
        "Rata-Rata" -> Color.Gray
        "Buruk" -> Color(0xFFFF6A07)
        "Terburuk" -> MaterialTheme.colorScheme.error
        else -> {MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)}
    }
}