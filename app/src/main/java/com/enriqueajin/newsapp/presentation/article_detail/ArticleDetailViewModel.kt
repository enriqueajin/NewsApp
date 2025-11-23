package com.enriqueajin.newsapp.presentation.article_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.use_case.AddArticleToFavoritesUseCase
import com.enriqueajin.newsapp.domain.use_case.CheckIsArticleFavoriteUseCase
import com.enriqueajin.newsapp.domain.use_case.DeleteArticleFromFavoritesUseCase
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailContract.Effect
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailContract.InternalEvent
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailContract.State
import com.enriqueajin.newsapp.presentation.article_detail.ArticleDetailContract.UiEvent
import com.enriqueajin.newsapp.util.updateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addArticleToFavoritesUseCase: AddArticleToFavoritesUseCase,
    private val deleteArticleFromFavoritesUseCase: DeleteArticleFromFavoritesUseCase,
    private val checkIsArticleFavoriteUseCase: CheckIsArticleFavoriteUseCase
) : ViewModel() {

    private val args: String = checkNotNull(savedStateHandle["article"]) { "This is null" }

    private val article by lazy {
        Json.decodeFromString(Article.serializer(), args)
    }

    private val _uiState = MutableStateFlow(State.EMPTY(article))
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<Effect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        pushInternal(InternalEvent.OnInit)
    }

    fun onPushEvent(event: UiEvent) {
        when (event) {
            UiEvent.OnFavoriteIconClick -> _uiState.value.handleFavoriteClick()
            UiEvent.OnBackPressed -> emitEffect(Effect.NavigateBack)
            is UiEvent.OnShareIconClick -> emitEffect(Effect.ShareArticleUrl(event.url))
        }
    }

    private fun pushInternal(internalEvent: InternalEvent) {
        when (internalEvent) {
            InternalEvent.OnInit -> init()
        }
    }

    private fun init() {
        updateArticle(article)
        checkArticleFavorite(article.url)
    }

    private fun State.handleFavoriteClick() {
        if (!isFavorite) {
            addArticleToFavorites(article)
        } else {
            deleteArticleFromFavorites(article)
        }
    }

    private fun addArticleToFavorites(article: Article) {
        viewModelScope.launch {
            addArticleToFavoritesUseCase(article)
        }
    }

    private fun deleteArticleFromFavorites(article: Article) {
        viewModelScope.launch {
            deleteArticleFromFavoritesUseCase(article)
        }
    }

    private fun checkArticleFavorite(articleId: String) {
        viewModelScope.launch {
            checkIsArticleFavoriteUseCase(articleId).collect { isFavorite ->
                _uiState.updateState { it.copy(isFavorite = isFavorite) }
            }
        }
    }

    private fun updateArticle(article: Article) = _uiState.updateState { it.copy(article = article) }

    private fun emitEffect(effect: Effect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}
