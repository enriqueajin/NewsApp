package com.enriqueajin.newsapp.presentation.article_detail

import com.enriqueajin.newsapp.domain.model.Article

sealed interface ArticleDetailEvent {

    data class AddFavorite(val article: Article): ArticleDetailEvent

    data class DeleteFavorite(val article: Article): ArticleDetailEvent

    data class CheckIsFavoriteArticle(val articleId: String): ArticleDetailEvent
}