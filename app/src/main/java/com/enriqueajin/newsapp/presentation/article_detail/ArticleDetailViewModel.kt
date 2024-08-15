package com.enriqueajin.newsapp.presentation.article_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.use_case.AddArticleToFavoritesUseCase
import com.enriqueajin.newsapp.domain.use_case.CheckIsArticleFavoriteUseCase
import com.enriqueajin.newsapp.domain.use_case.DeleteArticleFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val addArticleToFavoritesUseCase: AddArticleToFavoritesUseCase,
    private val deleteArticleFromFavoritesUseCase: DeleteArticleFromFavoritesUseCase,
    private val checkIsArticleFavoriteUseCase: CheckIsArticleFavoriteUseCase
) : ViewModel() {

    private val _isArticleFavorite = MutableStateFlow(false)
    val isArticleFavorite = _isArticleFavorite.asStateFlow()

    fun addArticleToFavorites(article: Article) {
        viewModelScope.launch {
            addArticleToFavoritesUseCase(article)
        }
    }

    fun deleteArticleFromFavorites(article: Article) {
        viewModelScope.launch {
            deleteArticleFromFavoritesUseCase(article)
        }
    }

    fun checkArticleFavorite(articleId: String) {
        viewModelScope.launch {
            checkIsArticleFavoriteUseCase(articleId).collectLatest { isFavorite ->
                _isArticleFavorite.value = isFavorite
            }
        }
    }
}