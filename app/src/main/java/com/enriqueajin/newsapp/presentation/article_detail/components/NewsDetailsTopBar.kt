package com.enriqueajin.newsapp.presentation.article_detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailsTopBar(
    isFavoriteArticle: Boolean,
    onShareArticle: () -> Unit,
    onAddFavorite: () -> Unit,
    onDeleteFavorite: () -> Unit,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.padding(horizontal = 13.dp),
        title = { },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = onShareArticle) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
            }
            IconButton(onClick = {
                if (isFavoriteArticle) {
                    onDeleteFavorite()
                } else {
                    onAddFavorite()
                }
            }) {
                val icon = if (isFavoriteArticle) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    )
}