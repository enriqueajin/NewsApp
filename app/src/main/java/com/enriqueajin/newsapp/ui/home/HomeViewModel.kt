package com.enriqueajin.newsapp.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.domain.GetAllTopNewsUseCase
import com.enriqueajin.newsapp.domain.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.util.KeywordProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTopNewsUseCase: GetAllTopNewsUseCase,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    private val _selectedNavIndex = mutableStateOf(0)
    val selectedNavIndex: State<Int> = _selectedNavIndex

    var localState = MutableStateFlow(HomeLocalUiState(keyword = KeywordProvider.getRandomKeyword()))

    private val _latestNews: Flow<List<NewsItem>> = getAllTopNewsUseCase(pageSize = "10")
    private val _newsByKeyword: Flow<List<NewsItem>> = getNewsByKeywordUseCase(keyword = localState.value.keyword, pageSize = "15")
    private val _newsByCategory: MutableStateFlow<List<NewsItem>> = MutableStateFlow(emptyList())

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )

    init {
        getMainNews()
    }

    fun getMainNews() {
        combine(
            _latestNews,
            _newsByKeyword,
            _newsByCategory
        ) { latestNews, newsByKeyword, newsByCategory ->
            _uiState.value = HomeUiState.Success(
                latestNews = latestNews,
                newsByKeyword = newsByKeyword,
                newsByCategory = newsByCategory
            )
        }.onStart {
            _uiState.value = HomeUiState.Loading
        }.catch {
            _uiState.value = HomeUiState.Error(it)

        }.launchIn(viewModelScope)
    }

    fun getNewsByCategory(category: String, pageSize: String) {
        getAllTopNewsUseCase(category, pageSize).onStart {
            _uiState.value = HomeUiState.Loading
        }.onEach { news ->
            _newsByCategory.value = news
        }.catch { error ->
            _uiState.value = HomeUiState.Error(error)
        }.launchIn(viewModelScope)
    }

    fun setSelectedNavIndex(index: Int) {
        _selectedNavIndex.value = index
    }
}