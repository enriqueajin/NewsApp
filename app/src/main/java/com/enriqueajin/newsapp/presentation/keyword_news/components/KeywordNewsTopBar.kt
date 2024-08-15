package com.enriqueajin.newsapp.presentation.keyword_news.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeywordNewsTopBarApp(title: String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
    )
}