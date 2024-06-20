package com.enriqueajin.newsapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.GetAllTopNewsUseCase
import com.enriqueajin.newsapp.domain.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.ui.home.tabs.LocalUiState
import com.enriqueajin.newsapp.ui.home.tabs.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getAllTopNewsUseCase: GetAllTopNewsUseCase,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    private val _selectedNavIndex = mutableStateOf(0)
    val selectedNavIndex: State<Int> = _selectedNavIndex

    var localState = MutableStateFlow(LocalUiState())

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        getMainNews()
    }

    fun getMainNews() {
        combine(
            getAllTopNewsUseCase(pageSize = "10"),
            getNewsByKeywordUseCase(keyword = localState.value.keyword)
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

    fun getNewsByCategory(category: String, pageSize: String) {
        val currentState = (_uiState.value as? NewsUiState.Success)

        getAllTopNewsUseCase(category, pageSize).onStart {
            _uiState.value = NewsUiState.Loading
        }.onEach { news ->
            _uiState.value = NewsUiState.Success(
                latestNews = currentState?.latestNews,
                newsByKeyword = currentState?.newsByKeyword,
                newsByCategory = news
            )
        }.catch { error ->
            _uiState.value = NewsUiState.Error(error)
        }.launchIn(viewModelScope)
    }

    fun setSelectedNavIndex(index: Int) {
        _selectedNavIndex.value = index
    }
}