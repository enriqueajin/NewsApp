package com.enriqueajin.newsapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor() : ViewModel() {

    private val _chipSelected = mutableStateOf("All")
    val chipSelected: State<String> = _chipSelected

    private val _selectedNavIndex = mutableStateOf(0)
    val selectedNavIndex: State<Int> = _selectedNavIndex

    fun setChipSelected(category: String) {
        _chipSelected.value = category
    }

    fun setSelectedNavIndex(index: Int) {
        _selectedNavIndex.value = index
    }
}