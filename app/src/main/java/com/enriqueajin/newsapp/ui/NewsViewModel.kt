package com.enriqueajin.newsapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor() : ViewModel() {

    private val _tabRowIndex = mutableStateOf(0)
    val tabRowIndex: State<Int> = _tabRowIndex

    private val _chipSelected = mutableStateOf("All")
    val chipSelected: State<String> = _chipSelected

    fun setTabRowIndex(index: Int) {
        _tabRowIndex.value = index
    }

    fun setChipSelected(category: String) {
        _chipSelected.value = category
    }
}