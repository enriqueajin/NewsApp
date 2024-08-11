package com.enriqueajin.newsapp.presentation.article_detail

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.enriqueajin.newsapp.R
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.presentation.article_detail.components.NewsDetailsTopBar
import com.enriqueajin.newsapp.presentation.ui.theme.DarkGray
import com.enriqueajin.newsapp.presentation.ui.theme.Purple80
import com.enriqueajin.newsapp.util.Constants.NO_AUTHOR
import com.enriqueajin.newsapp.util.Constants.NO_CONTENT
import com.enriqueajin.newsapp.util.Constants.NO_DATE
import com.enriqueajin.newsapp.util.Constants.NO_TITLE
import com.enriqueajin.newsapp.util.DateUtils.formatDate
import com.enriqueajin.newsapp.util.DummyDataProvider

@Composable
fun ArticleDetailScreen(
    article: Article,
    isFavoriteArticle: Boolean,
    event: (ArticleDetailEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        event(ArticleDetailEvent.CheckIsFavoriteArticle(article.url))
    }

    Scaffold(topBar = {
        NewsDetailsTopBar(
            article = article,
            isFavoriteArticle = isFavoriteArticle,
            onShareArticle = {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, it.url)
                intent.type = "text/plain"
                context.startActivity(intent)
            },
            onAddFavorite = {
                event(ArticleDetailEvent.AddFavorite(it))
                event(ArticleDetailEvent.CheckIsFavoriteArticle(article.url))
            },
            onDeleteFavorite = {
                event(ArticleDetailEvent.DeleteFavorite(it))
                event(ArticleDetailEvent.CheckIsFavoriteArticle(article.url))
            },
            onBackPressed = { onBackPressed() }
        )
    }) {
        Box(modifier = Modifier
            .padding(it)
            .padding(horizontal = 30.dp)
            .fillMaxSize()
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssistChip(
                        onClick = {},
                        label = {
                            val text = article.author ?: NO_AUTHOR
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
                        text = formatDate(article.publishedAt ?: NO_DATE),
                        color = DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = article.title ?: NO_TITLE,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40.sp
                )
                Spacer(modifier = Modifier.height(25.dp))
                if (article.urlToImage == null) {
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
                        model = article.urlToImage,
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
                    text = article.content ?: NO_CONTENT,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NewsDetailScreenPreview() {
    ArticleDetailScreen(
        article = DummyDataProvider.getAllNewsItems().first(),
        isFavoriteArticle = true,
        event = {},
        onBackPressed = {}
    )
}