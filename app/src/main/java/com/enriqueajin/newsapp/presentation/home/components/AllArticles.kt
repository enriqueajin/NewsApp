package com.enriqueajin.newsapp.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.home.HomeUiState
import com.enriqueajin.newsapp.presentation.ui.theme.DarkGray
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_ARTICLES_LIST
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_CIRCULAR_PROGRESS
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_ERROR
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_KEYWORD
import com.enriqueajin.newsapp.util.TestTags.ALL_ARTICLES_SEE_ALL

@Composable
fun AllArticles(
    state: HomeUiState,
    onSeeAllClicked: (String) -> Unit,
    onItemClicked: (Article) -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            HomeUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                    .testTag(ALL_ARTICLES_CIRCULAR_PROGRESS)
                )
            }
            is HomeUiState.Success -> {
                ArticlesLists(
                    latestArticles = state.latestArticles ?: emptyList(),
                    articlesByKeyword = state.articlesByKeyword ?: emptyList(),
                    keyword = state.keyword ?: "",
                    onSeeAllClicked = onSeeAllClicked,
                    onItemClicked = onItemClicked
                )
            }
            is HomeUiState.Error -> {
                val error = state.throwable
                Text(
                    text = "$error",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag(ALL_ARTICLES_ERROR)
                )
            }
        }
    }
}

@Composable
fun ArticlesLists(
    latestArticles: List<Article>,
    articlesByKeyword: List<Article>,
    keyword: String,
    onSeeAllClicked: (String) -> Unit,
    onItemClicked: (Article) -> Unit,
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .testTag(ALL_ARTICLES_ARTICLES_LIST)
    ) {
        item {
            Text(
                text = "Latest news",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 30.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            LatestArticlesCarousel(articles = latestArticles, onItemClicked = onItemClicked)
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = keyword,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag(ALL_ARTICLES_KEYWORD)
                )
                Text(
                    text = "See All",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray,
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .clickable {
                            onSeeAllClicked(keyword)
                        }
                        .testTag(ALL_ARTICLES_SEE_ALL),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            ArticlesByKeyword(articles = articlesByKeyword, onItemClicked = onItemClicked)
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}