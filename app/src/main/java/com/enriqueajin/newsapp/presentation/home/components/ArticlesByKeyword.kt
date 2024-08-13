package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.TestTags.ARTICLES_BY_KEYWORD_ITEM
import com.enriqueajin.newsapp.util.TestTags.ARTICLES_BY_KEYWORD_LAZY_ROW

@Composable
fun ArticlesByKeyword(news: List<Article>, onItemClicked: (Article) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp)
    ) {
        LazyRow(modifier = Modifier.testTag(ARTICLES_BY_KEYWORD_LAZY_ROW)) {
            items(news) { article ->
                ArticlesByKeywordItem(
                    modifier = Modifier.testTag(ARTICLES_BY_KEYWORD_ITEM + news.indexOf(article)),
                    article = article
                ) { item -> onItemClicked(item) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesByKeywordPreview() {
    ArticlesByKeyword(news = DummyDataProvider.getAllNewsItems()) {}
}