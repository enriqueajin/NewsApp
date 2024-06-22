package com.enriqueajin.newsapp.ui.keyword_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.GetNewsByKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class KeywordNewsViewModel @Inject constructor(
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
): ViewModel() {

    val localState = MutableStateFlow(KeywordNewsLocalUiState())

    private val _uiState = MutableStateFlow<KeywordNewsUiState>(KeywordNewsUiState.Loading)
    val uiState: StateFlow<KeywordNewsUiState> = _uiState.asStateFlow()

    fun getNewsByKeyword(keyword: String, pageSize: String) {
        getNewsByKeywordUseCase(keyword, pageSize).onEach { news ->
            _uiState.value = KeywordNewsUiState.Success(newsByKeyword = news)
        }.catch { error ->
            _uiState.value = KeywordNewsUiState.Error(error)
        }.launchIn(viewModelScope)
    }
}