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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase,
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
) : ViewModel() {

    private val _selectedNavIndex = mutableStateOf(0)
    val selectedNavIndex: State<Int> = _selectedNavIndex

    val category = MutableStateFlow("All")

    var localState = MutableStateFlow(HomeLocalUiState(keyword = KeywordProvider.getRandomKeyword()))

    val latestNews: Flow<PagingData<NewsItem>> = getNewsByCategoryUseCase(pageSize = 10).cachedIn(viewModelScope)
    val newsByKeyword: Flow<PagingData<NewsItem>> = getNewsByKeywordUseCase(keyword = localState.value.keyword, pageSize = 20).cachedIn(viewModelScope)
    val newsByCategory: Flow<PagingData<NewsItem>> = category.flatMapLatest { category ->
        when (category) {
            "general" -> {
                getNewsByCategoryUseCase(
                    category = category,
                    pageSize = 20,
                ).cachedIn(viewModelScope)
            }
            else -> {
                getNewsByCategoryUseCase(
                    category = category,
                    pageSize = 20,
                    needsPagination = true
                ).cachedIn(viewModelScope)
            }
        }
    }

    fun setSelectedNavIndex(index: Int) {
        _selectedNavIndex.value = index
    }
}