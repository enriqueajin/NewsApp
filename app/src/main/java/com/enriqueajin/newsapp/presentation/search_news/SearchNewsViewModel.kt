package com.enriqueajin.newsapp.presentation.search_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.domain.model.Article
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

    private val _articles = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val articles = _articles.asStateFlow()

    @OptIn(FlowPreview::class)
    fun queryNews(query: String) {
        viewModelScope.launch {
            getNewsByKeywordUseCase(keyword = query)
                .debounce(500L)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _articles.value = it
                }
        }
    }

    fun onEvent(event: SearchNewsEvent) {
        when (event) {
            is SearchNewsEvent.OnQueryNews -> queryNews(event.query)
        }
    }
}