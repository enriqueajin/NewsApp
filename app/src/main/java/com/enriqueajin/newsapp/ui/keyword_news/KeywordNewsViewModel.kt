package com.enriqueajin.newsapp.ui.keyword_news

import androidx.lifecycle.ViewModel
import com.enriqueajin.newsapp.domain.GetAllTopNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeywordNewsViewModel @Inject constructor(
    getAllTopNewsUseCase: GetAllTopNewsUseCase
): ViewModel() {

}