package com.ayukrisna.skinsift.view.ui.screen.assessment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.view.ui.component.AssessmentNavButton
import com.ayukrisna.skinsift.view.ui.component.AssessmentQuestion
import com.ayukrisna.skinsift.view.ui.component.AssessmentSelector


@Composable
fun FunctionAssessmentScreen (
    paddingValues: PaddingValues,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectionItems: List<String> =
        listOf(
            "Anti-aging",
            "Melembapkan kulit",
            "Menutrisi kulit",
            "Mencerahkan kulit",
            "Menyegarkan kulit",
            "Menenangkan kulit",
            "Tidak, hanya fungsi utama"
        )
    Scaffold(
        topBar = {
            AssessmentAppBar(
                title = "Asesmen Personalisasi",
                onBackClick = { onBackClick() }
            )
        },
        floatingActionButton = {
            AssessmentNavButton({onNextClick()}, {onBackClick()} )
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        top = paddingValues.calculateTopPadding() + 72.dp,
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        bottom = innerPadding.calculateBottomPadding()
                    ),
            ) {
                AssessmentQuestion("3. Apakah kamu mencari produk dengan fungsi tambahan?")
                Spacer(modifier = Modifier.height(12.dp))
                AssessmentSelector(selectionItems)
            }
        },
    )
}