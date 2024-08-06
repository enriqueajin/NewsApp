package com.enriqueajin.newsapp.presentation.keyword_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.enriqueajin.newsapp.domain.model.Article
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

    private val _newsByKeyword = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val newsByKeyword: StateFlow<PagingData<Article>> = _newsByKeyword.asStateFlow()

    private fun getNewsByKeyword(keyword: String) {
        viewModelScope.launch {
            getNewsByKeywordUseCase(keyword)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _newsByKeyword.value = it
                }
        }
    }

    fun onEvent(event: KeywordNewsEvent) {
        when (event) {
            is KeywordNewsEvent.GetArticlesByKeyword -> getNewsByKeyword(event.keyword)
        }
    }
}