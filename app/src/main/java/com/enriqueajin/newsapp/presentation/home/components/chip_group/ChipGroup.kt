package com.enriqueajin.newsapp.presentation.home.components.chip_group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enriqueajin.newsapp.presentation.home.HomeViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun ChipGroup(
    homeViewModel: HomeViewModel,
    categories: List<String>,
    selected: String,
    onChipSelected: (String) -> Unit,
) {
    val scrollPosition = homeViewModel.scrollPosition.collectAsStateWithLifecycle()

    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = scrollPosition.value)

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }
            .debounce(500L)
            .collectLatest { index ->
                homeViewModel.setScrollPosition(index)
            }
    }

    LazyRow(
        state = lazyListState,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 25.dp, vertical = 15.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onChipSelected(category) },
                label = { Text(text = category) },
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChipGroupPreview() {
//    val categories = listOf("All", "Science", "Sports", "Politics", "Business", "Psychology")
//    var selected by rememberSaveable { mutableStateOf("Science") }
//    ChipGroup(
//        categories = categories,
//        selected = selected,
//        onChipSelected = { category -> selected = category }
//    )
}