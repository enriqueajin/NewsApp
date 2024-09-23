package com.enriqueajin.newsapp.presentation.home.components

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriqueajin.newsapp.util.Constants.CATEGORIES
import com.enriqueajin.newsapp.util.TestTags.CATEGORY
import com.enriqueajin.newsapp.util.TestTags.CATEGORY_GROUP_LAZY_ROW
import kotlinx.coroutines.FlowPreview

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun CategoryGroup(
    scrollPosition: Int,
    categories: List<String>,
    selected: String,
    onChipSelected: (String) -> Unit,
    onCategoryScrollPositionChanged: (Int) -> Unit,
) {
//    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = scrollPosition)

//    LaunchedEffect(lazyListState) {
//        snapshotFlow {
//            lazyListState.firstVisibleItemIndex
//        }
//            .debounce(500L)
//            .collectLatest { index ->
//                onCategoryScrollPositionChanged(index)
//            }
//    }

    LazyRow(
//        state = lazyListState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 15.dp)
            .testTag(CATEGORY_GROUP_LAZY_ROW)
    ) {
        items(categories) { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onChipSelected(category) },
                label = { Text(text = category) },
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .testTag(CATEGORY + categories.indexOf(category))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChipGroupPreview() {
    CategoryGroup(
        scrollPosition = 0,
        categories = CATEGORIES,
        selected = "Science",
        onChipSelected = { _ ->},
        onCategoryScrollPositionChanged = {}
    )
}