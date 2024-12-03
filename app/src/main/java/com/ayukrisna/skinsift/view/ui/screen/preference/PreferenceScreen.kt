package com.ayukrisna.skinsift.view.ui.screen.preference

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.R
import com.ayukrisna.skinsift.domain.model.IngredientModel
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import com.ayukrisna.skinsift.view.ui.screen.dictionary.dummyDictionaryList
import com.ayukrisna.skinsift.view.ui.screen.dictionary.IngredientsItem

@Composable
fun PreferenceScreen (
    paddingValues: PaddingValues,
    onNavigateToDetail: () -> Unit,
    onNavigateToAdd: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Suka", "Tidak Suka")

    Scaffold (
        topBar = {
            PreferenceAppBar("Bahan Tersimpan", onBackClick)
        },
        floatingActionButton = {
            AddPreferenceButton(onNavigateToAdd)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        end = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        bottom = paddingValues.calculateBottomPadding() - 32.dp)
                ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }
                when (selectedTabIndex) {
                    0 -> LikeContent(dummyDictionaryList, onNavigateToDetail)
                    1 -> DislikeContent()
                }
            }
        }
    )
}

@Composable
fun LikeContent(lists: List<IngredientModel>, onNavigateToDetail: () -> Unit) {
    FilledPreference(lists, onNavigateToDetail)
}

@Composable
fun DislikeContent() {
    NullPreference()
}

@Composable
fun FilledPreference(lists: List<IngredientModel>, onNavigateToDetail: () -> Unit) {
    LazyColumn {
        items(lists) { item ->
            IngredientsItem(item) { onNavigateToDetail() }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Composable
fun NullPreference() {
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
                text = "Kamu belum menyimpan ingredients yang kamu suka, nih.\n" +
                        "Yuk, tambahkan dulu!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun PreferenceAppBar(title: String,
                     onBackClick: () -> Unit,
                     modifier: Modifier = Modifier) {
    CenterAppBar( title = title,
        onBackClick = {onBackClick()}
    )
}

@Composable
fun AddPreferenceButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(Icons.Filled.Add, "Add preference action button.") },
        text = { Text("Tambah Notes") },
    )
}

//@Preview (showBackground = true)
//@Composable
//fun PreferenceScreenPreview() {
//    SkinSiftTheme {
//        PreferenceScreen()
//    }
//}
//
