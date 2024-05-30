package com.enriqueajin.newsapp.ui.model

data class ChipCategoryItem(
    val label: String,
    var selected: Boolean = false,
    val onClick: () -> Unit
)