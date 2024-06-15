package com.enriqueajin.newsapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.GetAllTopNewsUseCase
import com.enriqueajin.newsapp.domain.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.ui.home.tabs.NewsUiState
import com.enriqueajin.newsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getAllTopNewsUseCase: GetAllTopNewsUseCase,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    private val _chipSelected = mutableStateOf("All")
    val chipSelected: State<String> = _chipSelected

    private val _selectedNavIndex = mutableStateOf(0)
    val selectedNavIndex: State<Int> = _selectedNavIndex

    private val _keywordSearch = mutableStateOf("Real Madrid")
    val keywordSearch: State<String> = _keywordSearch

    private val _uiState = mutableStateOf(NewsUiState())
    val uiState: State<NewsUiState> = _uiState

    init {
        getAllTopNews()
        getNewsByKeyword(_keywordSearch.value)
    }

    private fun getAllTopNews() {
        getAllTopNewsUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> _uiState.value = _uiState.value.copy(error = result.message ?: "An unexpected error occurred.")
                is Resource.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
                is Resource.Success -> _uiState.value = _uiState.value.copy(allTopNewsList = result.data ?: emptyList(), isLoading = false)
            }
        }.launchIn(viewModelScope)
    }

    private fun getNewsByKeyword(keyword: String) {
        getNewsByKeywordUseCase(keyword).onEach { result ->
            when (result) {
                is Resource.Error -> _uiState.value = _uiState.value.copy(error = result.message ?: "An unexpected error occurred.")
                is Resource.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
                is Resource.Success -> _uiState.value = _uiState.value.copy(newsByKeywordList = result.data ?: emptyList(), isLoading = false)
            }
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