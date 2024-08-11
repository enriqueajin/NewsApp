package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.util.DummyDataProvider

@Composable
fun ArticlesByKeyword(news: List<Article>, onItemClicked: (Article) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp)
    ) {
        LazyRow {
            items(news) { article ->
                ArticlesByKeywordItem(article) { item -> onItemClicked(item) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesByKeywordPreview() {
    ArticlesByKeyword(news = DummyDataProvider.getAllNewsItems()) {}
}