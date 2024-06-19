package com.enriqueajin.newsapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.GetAllTopNewsUseCase
import com.enriqueajin.newsapp.domain.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.ui.home.tabs.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getAllTopNewsUseCase: GetAllTopNewsUseCase,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    private val _chipSelected = mutableStateOf("All")
    var chipSelected: State<String> = _chipSelected

    private val _selectedNavIndex = mutableStateOf(0)
    val selectedNavIndex: State<Int> = _selectedNavIndex

    private val _keywordSearch = mutableStateOf("Real Madrid")
    val keywordSearch: State<String> = _keywordSearch

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        getMainNews()
    }

    fun getMainNews() {
        combine(
            getAllTopNewsUseCase(),
            getNewsByKeywordUseCase(_keywordSearch.value)
        ) { latestNews, newsByKeyword ->
            when {
                latestNews.isNullOrEmpty() -> _uiState.value = NewsUiState.Success(newsByKeyword = newsByKeyword)
                newsByKeyword.isNullOrEmpty() -> _uiState.value = NewsUiState.Success(latestNews = latestNews)
                else -> _uiState.value = NewsUiState.Success(latestNews, newsByKeyword)
            }

        }.catch {
            _uiState.value = NewsUiState.Error(it)

        }.launchIn(viewModelScope)
    }

    fun setChipSelected(category: String) {
        _chipSelected.value = category
    }

    fun setSelectedNavIndex(index: Int) {
        _selectedNavIndex.value = index
    }

    fun setKeywordSearch(keyword: String) {
        _keywordSearch.value = keyword
    }
}