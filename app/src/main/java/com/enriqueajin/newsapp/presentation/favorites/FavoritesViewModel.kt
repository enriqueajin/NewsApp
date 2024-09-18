package com.enriqueajin.newsapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.DataError
import com.enriqueajin.newsapp.domain.Result
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.use_case.GetFavoriteArticlesUseCase
import com.enriqueajin.newsapp.presentation.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase
): ViewModel() {

    private val favoriteArticles: Flow<Result<List<Article>, DataError.Database>> = getFavoriteArticlesUseCase()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val uiState = searchText
        .combine(favoriteArticles) { text, favorites ->
            when (favorites) {
                is Result.Error -> FavoritesUiState.Error(favorites.error.asUiText())
                is Result.Success -> {
                    if (text.isBlank()) {
                        FavoritesUiState.Success(favorites.data)
                    } else {
                        val filteredArticles = favorites.data.filter { it.title!!.contains(text, ignoreCase = true) }
                        FavoritesUiState.Success(filteredArticles)
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState.Loading
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}