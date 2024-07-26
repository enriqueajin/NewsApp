package com.enriqueajin.newsapp.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.domain.use_case.GetNewsByCategoryUseCase
import com.enriqueajin.newsapp.domain.use_case.GetNewsByKeywordUseCase
import com.enriqueajin.newsapp.util.KeywordProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    private val _selectedNavIndex = mutableStateOf(0)
    val selectedNavIndex: State<Int> = _selectedNavIndex

    private val _category = MutableStateFlow("All")
    val category = _category.asStateFlow()

    var localState = MutableStateFlow(HomeLocalUiState(keyword = KeywordProvider.getRandomKeyword()))

    private val _scrollPosition = MutableStateFlow(0)
    val scrollPosition = _scrollPosition.asStateFlow()

    val latestNews: StateFlow<PagingData<NewsItem>> = getNewsByCategoryUseCase()
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    val newsByKeyword: StateFlow<PagingData<NewsItem>> = getNewsByKeywordUseCase(keyword = localState.value.keyword)
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    val newsByCategory: StateFlow<PagingData<NewsItem>> = category.flatMapLatest { category ->

        when (category) {
            "general" -> {
                getNewsByCategoryUseCase(category = category)
                    .cachedIn(viewModelScope)

            }
            else -> {
                getNewsByCategoryUseCase(category = category, needsPagination = true)
                    .cachedIn(viewModelScope)

            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PagingData.empty()
    )

    fun setCategory(category: String) {
        _category.value = category
    }

    fun setSelectedNavIndex(index: Int) {
        _selectedNavIndex.value = index
    }

    fun setScrollPosition(position: Int) {
        _scrollPosition.value = position

    }
}