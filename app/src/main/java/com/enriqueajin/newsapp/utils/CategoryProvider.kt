package com.enriqueajin.newsapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.enriqueajin.newsapp.ui.model.ChipCategoryItem

object CategoryProvider {
    @Composable
    fun getCategories(categories: List<String>): List<ChipCategoryItem> {
        return categories.map { category ->
            var selected by rememberSaveable { mutableStateOf(false) }

            ChipCategoryItem(
                label = category,
                selected = selected,
                onClick = { selected = !selected }
            )
        }
    }
}