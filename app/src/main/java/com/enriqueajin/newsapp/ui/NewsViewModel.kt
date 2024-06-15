package com.enriqueajin.newsapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.GetAllTopNewsUseCase
import com.enriqueajin.newsapp.domain.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.ui.home.tabs.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    getAllTopNewsUseCase: GetAllTopNewsUseCase,
    getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    private val _chipSelected = mutableStateOf("All")
    var chipSelected: State<String> = _chipSelected

    private val _selectedNavIndex = mutableStateOf(0)
    val selectedNavIndex: State<Int> = _selectedNavIndex

    private val _keywordSearch = mutableStateOf("Real Madrid")
    val keywordSearch: State<String> = _keywordSearch

    val uiState: StateFlow<NewsUiState> =
        combine(getAllTopNewsUseCase(), getNewsByKeywordUseCase(_keywordSearch.value)) { latestNews, newsByKeyword ->
            when {
                latestNews.isNullOrEmpty() && newsByKeyword.isNullOrEmpty() -> NewsUiState.Error(Throwable("Error occurred when retrieving all of the news."))
                latestNews.isNullOrEmpty() -> NewsUiState.Success(newsByKeyword = newsByKeyword)
                newsByKeyword.isEmpty() -> NewsUiState.Success(latestNews = latestNews)
                else -> NewsUiState.Success(latestNews, newsByKeyword)
            }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NewsUiState.Loading
        )

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