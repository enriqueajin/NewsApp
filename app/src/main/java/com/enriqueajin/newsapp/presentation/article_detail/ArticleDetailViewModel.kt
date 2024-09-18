package com.enriqueajin.newsapp.presentation.article_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.use_case.AddArticleToFavoritesUseCase
import com.enriqueajin.newsapp.domain.use_case.CheckIsArticleFavoriteUseCase
import com.enriqueajin.newsapp.domain.use_case.DeleteArticleFromFavoritesUseCase
import com.enriqueajin.newsapp.presentation.SnackbarController
import com.enriqueajin.newsapp.presentation.SnackbarEvent
import com.enriqueajin.newsapp.presentation.UiText
import com.enriqueajin.newsapp.presentation.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val addArticleToFavoritesUseCase: AddArticleToFavoritesUseCase,
    private val deleteArticleFromFavoritesUseCase: DeleteArticleFromFavoritesUseCase,
    private val checkIsArticleFavoriteUseCase: CheckIsArticleFavoriteUseCase
) : ViewModel() {

    private val eventChannel = Channel<UserEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _isArticleFavorite = MutableStateFlow(false)
    val isArticleFavorite = _isArticleFavorite.asStateFlow()

    fun addArticleToFavorites(article: Article) {
        viewModelScope.launch {
            addArticleToFavoritesUseCase(article).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        val error = result.error.asUiText()
                        eventChannel.send(UserEvent.Error(error))
                    }
                    is Result.Success -> {
                        result.data
                    }
                }
            }
        }
    }

    fun deleteArticleFromFavorites(article: Article) {
        viewModelScope.launch {
            deleteArticleFromFavoritesUseCase(article).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        val error = result.error.asUiText()
                        eventChannel.send(UserEvent.Error(error))
                    }
                    is Result.Success -> {
                        result.data
                    }
                }

            }
        }
    }

    fun checkArticleFavorite(articleId: String) {
        viewModelScope.launch {
            checkIsArticleFavoriteUseCase(articleId).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        val error = result.error.asUiText()
                        eventChannel.send(UserEvent.Error(error))
                    }
                    is Result.Success -> {
                        val isFavorite: Boolean = result.data
                        _isArticleFavorite.value = isFavorite
                    }
                }
            }
        }
    }

    fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = message
                )
            )
        }
    }
}

sealed interface UserEvent {
    data class Error(val error: UiText): UserEvent
//    data class DeleteArticleError(val error: UiText): UserEvent
//    data class CheckArticleExistsError(val error: UiText): UserEvent
}