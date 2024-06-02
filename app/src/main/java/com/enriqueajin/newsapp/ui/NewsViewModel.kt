package com.enriqueajin.newsapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.GetAllTopNewsUseCase
import com.enriqueajin.newsapp.domain.GetNewsByKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getAllTopNews: GetAllTopNewsUseCase,
    private val getNewsByKeyword: GetNewsByKeywordUseCase
) : ViewModel() {

    private val _chipSelected = mutableStateOf("All")
    val chipSelected: State<String> = _chipSelected

    private val _selectedNavIndex = mutableStateOf(0)
    val selectedNavIndex: State<Int> = _selectedNavIndex

    private val _keywordSearch = mutableStateOf("Real Madrid")
    val keywordSearch: State<String> = _keywordSearch

    val allTopNews = flow {
        val response = getAllTopNews()
        if (response.isNotEmpty()) {
            emit(response)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    val newsByKeyword = flow {
        val keyword = _keywordSearch.value
        val response = getNewsByKeyword(keyword)
        if (response.isNotEmpty()) {
            emit(response)
        }
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