package com.enriqueajin.newsapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.GetAllTopNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getAllTopNews: GetAllTopNewsUseCase
) : ViewModel() {

    private val _chipSelected = mutableStateOf("All")
    val chipSelected: State<String> = _chipSelected

    private val _selectedNavIndex = mutableStateOf(0)
    val selectedNavIndex: State<Int> = _selectedNavIndex

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

    fun setChipSelected(category: String) {
        _chipSelected.value = category
    }

    fun setSelectedNavIndex(index: Int) {
        _selectedNavIndex.value = index
    }
}