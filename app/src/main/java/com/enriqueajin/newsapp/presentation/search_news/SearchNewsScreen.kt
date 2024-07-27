package com.enriqueajin.newsapp.presentation.search_news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SearchNewsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Search News Screen", modifier = Modifier.align(Alignment.Center))
    }
}