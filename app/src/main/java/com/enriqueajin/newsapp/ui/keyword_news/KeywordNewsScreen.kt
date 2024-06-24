package com.enriqueajin.newsapp.ui.keyword_news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.ui.KeywordNews
import com.enriqueajin.newsapp.ui.home.tabs.news.components.keyword_news.NewsListItem
import com.enriqueajin.newsapp.ui.keyword_news.components.KeywordNewsTopBarApp

@Composable
fun KeywordNewsScreen(
    keywordNewsViewModel: KeywordNewsViewModel,
    args: KeywordNews,
    onItemClicked: (NewsItem) -> Unit,
    onBackPressed: () -> Unit
) {
    val uiState by keywordNewsViewModel.uiState.collectAsStateWithLifecycle()
    val keyword = args.keyword

    keywordNewsViewModel.getNewsByKeyword(keyword, "25")
    Scaffold(topBar = { KeywordNewsTopBarApp(keyword) { onBackPressed()} }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            when (uiState) {
                is KeywordNewsUiState.Error -> { Text(text = "${(uiState as KeywordNewsUiState.Error).throwable}") }
                KeywordNewsUiState.Loading -> { CircularProgressIndicator(Modifier.align(Alignment.Center)) }
                is KeywordNewsUiState.Success -> {
                    val news = (uiState as KeywordNewsUiState.Success).newsByKeyword ?: emptyList()
                    LazyColumn {
                        items(news) {  item ->
                            NewsListItem(item) { newsItem ->
                                onItemClicked(newsItem)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KeywordNewsScreenPreview() {
//    KeywordNewsScreen()
}