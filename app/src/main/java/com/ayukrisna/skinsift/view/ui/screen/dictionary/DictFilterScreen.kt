package com.ayukrisna.skinsift.view.ui.screen.dictionary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar

@Composable
fun DictFilterScreen(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            SaveFilterFab(onClick = {
                println("Filter Saved!")
                onBackClick()
            })
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
        topBar = {
            DictFilterAppBar(
                title = "Filter Bahan Skincare",
                onBackClick = { onBackClick() }
            )},
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        top = paddingValues.calculateTopPadding() + 72.dp,
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {
                FilterSection("Rating", listOf("Terbaik", "Baik", "Rata-Rata", "Buruk", "Terburuk", "Belum Dinilai"))
                Spacer(modifier = Modifier.height(8.dp))
                FilterSection("Kategori", listOf("Antioksidan", "Ekstrak Tumbuhan", "Meratakan warna kulit", "Scrub & Exfoliating", "Hidrasi", "Memudarkan Flek Hitam"))
            }
        }
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSection(title: String, options: List<String> ) {
//    var selected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        FlowRow (
            horizontalArrangement = Arrangement.Start
        ){
            options.forEach() { option ->
                var selected by remember { mutableStateOf(false) }
                FilterChip(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 8.dp, 0.dp),
                    label = {Text(option)},
                    selected = selected,
                    onClick = { selected = !selected },
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon =
                    if (selected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
            }
        }
    }
}

@Composable
fun SaveFilterFab(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Simpan Filter",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictFilterAppBar(title: String, onBackClick: () -> Unit, ) {
    CenterAppBar(
        title = title,
        onBackClick = {onBackClick()}
    )
}