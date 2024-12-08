package com.ayukrisna.skinsift.view.ui.screen.dictionary.filterdictionary

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ayukrisna.skinsift.data.remote.response.ingredients.Filter
import com.ayukrisna.skinsift.view.ui.component.CenterAppBar
import org.koin.androidx.compose.koinViewModel
import com.ayukrisna.skinsift.util.Result
import com.ayukrisna.skinsift.view.ui.component.ErrorLayout
import com.ayukrisna.skinsift.view.ui.component.LoadingProgress

@Composable
fun DictFilterScreen(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onNavigateToDictionary: (List<String>?, List<String>?) -> Unit,
    viewModel: DictFilterViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val filterState by viewModel.filterState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchFilter()
    }

    Scaffold(
        floatingActionButton = {
            SaveFilterFab(onClick = {
                val selectedFilters = viewModel.selectedFilters.value
                Log.d("FilterSelection", "Selected Filters: $selectedFilters")

                val rating: List<String>? = selectedFilters["rating"]
                val benefit: List<String>? = selectedFilters["benefitidn"]

                onNavigateToDictionary(rating, benefit)
            })
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
        topBar = {
            DictFilterAppBar(
                title = "Filter Bahan Skincare",
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
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {
                when (filterState) {
                    is Result.Idle -> Text("Idle State")
                    is Result.Loading -> LoadingProgress()
                    is Result.Success -> {
                        val filter: Filter = (filterState as Result.Success<Filter>).data
                        FilterSection("rating", filter.rating, viewModel)
                        Spacer(modifier = Modifier.height(24.dp))
                        FilterSection("benefitidn", filter.benefitidn, viewModel)
                    }
                    is Result.Error -> {
                        val error = (filterState as Result.Error).error
                        ErrorLayout(error = error)
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSection(category: String, options: List<String?>?, viewModel: DictFilterViewModel) {
    val title =
        if (category == "rating") "Rating"
        else "Kategori"

    val selectedFilters by viewModel.selectedFilters.collectAsState()

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
        if (options != null) {
            FlowRow (
                horizontalArrangement = Arrangement.Start
            ){
                options.forEach { option ->
                    val isSelected = selectedFilters[category]?.contains(option) == true
                    FilterChip(
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 8.dp, 0.dp),
                        label = {
                            if (option != null) {
                                Text(option)
                            }
                        },
                        selected = isSelected,
                        onClick = {
                            if (option != null) {
                                viewModel.toggleFilter(category, option)
                                Log.d("FilterSection", "Option: $option, Selected: $isSelected")
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        leadingIcon =
                        if (isSelected) {
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
        } else {
            Text(
                text = "Belum ada filter.",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
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