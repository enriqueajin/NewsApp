package com.enriqueajin.newsapp.ui.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriqueajin.newsapp.ui.model.ChipCategoryItem
import com.enriqueajin.newsapp.utils.CategoryProvider.getCategories

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipGroup(categories: List<ChipCategoryItem>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 25.dp)
    ) {
        LazyRow {

            items(categories) { category ->
                FilterChip(
                    selected = if (category.label == "All") !category.selected else category.selected,
                    onClick = { category.onClick() },
                    label = { Text(text = category.label) },
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChipGroupPreview() {
    val categories = listOf("All", "Science", "Sports", "Politics", "Business", "Psychology")
    ChipGroup(categories = getCategories(categories))
}