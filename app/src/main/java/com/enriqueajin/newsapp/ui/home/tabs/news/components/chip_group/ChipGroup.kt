package com.enriqueajin.newsapp.ui.home.tabs.news.components.chip_group

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipGroup(
    categories: List<String>,
    selected: String,
    onChipSelected: (String) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 25.dp)
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
    val categories = listOf("All", "Science", "Sports", "Politics", "Business", "Psychology")
    var selected by rememberSaveable { mutableStateOf("Science") }
    ChipGroup(
        categories = categories,
        selected = selected,
        onChipSelected = { category -> selected = category }
    )
}