package com.enriqueajin.newsapp.presentation.search_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.domain.use_case.GetNewsByKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
): ViewModel() {

    private val _articlesSearched = MutableStateFlow<PagingData<NewsItem>>(PagingData.empty())
    val articlesSearched = _articlesSearched.asStateFlow()

    @OptIn(FlowPreview::class)
    fun queryNews(query: String) {
        viewModelScope.launch {
            getNewsByKeywordUseCase(keyword = query, needsPagination = true)
                .debounce(500L)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _articlesSearched.value = it
                }
        }
    }
}