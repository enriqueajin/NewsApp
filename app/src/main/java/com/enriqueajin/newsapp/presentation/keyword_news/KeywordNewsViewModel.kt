package com.enriqueajin.newsapp.presentation.keyword_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.data.network.model.NewsItem
import com.enriqueajin.newsapp.domain.use_case.GetNewsByKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordNewsViewModel @Inject constructor(
    private val getNewsByKeywordUseCase: GetNewsByKeywordUseCase
): ViewModel() {

    private val _newsByKeyword = MutableStateFlow<PagingData<NewsItem>>(PagingData.empty())
    val newsByKeyword: StateFlow<PagingData<NewsItem>> = _newsByKeyword.asStateFlow()

    fun getNewsByKeyword(keyword: String) {
        viewModelScope.launch {
            getNewsByKeywordUseCase(keyword = keyword, needsPagination = true)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _newsByKeyword.value = it
                }
        }
    }
}