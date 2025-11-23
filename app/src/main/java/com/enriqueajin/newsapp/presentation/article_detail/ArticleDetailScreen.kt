package com.enriqueajin.newsapp.presentation.article_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailContract.Effect
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailContract.State
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailContract.UiEvent
import com.enriqueajin.newsapp.presentation.article_detail.components.NewsDetailsTopBar
import com.enriqueajin.newsapp.presentation.ui.theme.DarkGray
import com.enriqueajin.newsapp.presentation.ui.theme.Purple80
import com.enriqueajin.newsapp.util.Constants.NO_AUTHOR
import com.enriqueajin.newsapp.util.Constants.NO_CONTENT
import com.enriqueajin.newsapp.util.Constants.NO_DATE
import com.enriqueajin.newsapp.util.Constants.NO_TITLE
import com.enriqueajin.newsapp.util.DateUtils.formatDate
import com.enriqueajin.newsapp.util.DummyDataProvider
import com.enriqueajin.newsapp.util.collectAsEffect

@Composable
fun ArticleDetailRoute(
    viewModel: ArticleDetailViewModel = hiltViewModel(),
    onEffect: (Effect) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.uiEffect.collectAsEffect { onEffect(it) }

    when {
        state.loading -> CircularProgressIndicator()
        state.error.isNotBlank() -> Text("There was an error")
        else -> {
            ArticleDetailScreen(
                state = state,
                onPushEvent = viewModel::onPushEvent,
            )
        }
    }
}

@Composable
fun ArticleDetailScreen(
    state: State,
    onPushEvent: (UiEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            NewsDetailsTopBar(
                isFavoriteArticle = state.isFavorite,
                onShareArticle = { onPushEvent(UiEvent.OnShareIconClick(state.article.url)) },
                onFavoriteIconClick = { onPushEvent(UiEvent.OnFavoriteIconClick) },
                onBackPressed = { onPushEvent(UiEvent.OnBackPressed) }
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AssistChip(
                    onClick = {},
                    label = {
                        val text = state.article.author ?: NO_AUTHOR
                        val maxLength = 30
                        Text(
                            text = if (text.length <= maxLength) text else text.substring(0, maxLength),
                            maxLines = 1,
                        )
                    },
                    enabled = false,
                    colors = AssistChipDefaults.assistChipColors(
                        disabledContainerColor = Purple80,
                        disabledLabelColor = Color.White
                    )
                )
                Text(
                    text = formatDate(state.article.publishedAt ?: NO_DATE),
                    color = DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = state.article.title ?: NO_TITLE,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp
            )
            Spacer(modifier = Modifier.height(25.dp))
            if (state.article.urlToImage == null) {
                Image(
                    painter = painterResource(id = R.drawable.no_image_available),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(230.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )

            } else {
                AsyncImage(
                    model = state.article.urlToImage,
                    error = painterResource(id = R.drawable.no_image_available),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = state.article.content ?: NO_CONTENT,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewsDetailScreenPreview() {
    ArticleDetailScreen(
        state = State.EMPTY(
            article = DummyDataProvider.getLatestNewsItems().first()
        ),
        onPushEvent = {},
    )
}
