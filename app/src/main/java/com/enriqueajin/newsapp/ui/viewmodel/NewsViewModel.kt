package com.enriqueajin.newsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enriqueajin.newsapp.domain.model.Article
import com.enriqueajin.newsapp.domain.usecases.GetNewsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase
) : ViewModel() {

    val news = MutableLiveData<List<Article>?>()

    fun getNewsByCategory(category: String?) {
        viewModelScope.launch {
            val result = getNewsByCategoryUseCase(category)

            if (!result.isNullOrEmpty()) {
                news.postValue(result)
            }
        }
    }
}