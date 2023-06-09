package com.nikolaswidad.oasesimpleapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikolaswidad.oasesimpleapp.data.NewsRepository
import com.nikolaswidad.oasesimpleapp.data.local.entity.NewsEntity
import com.nikolaswidad.oasesimpleapp.data.remote.response.ArticlesItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val _newsList = MutableStateFlow<List<ArticlesItem>>(emptyList())

    val newsList : StateFlow<List<ArticlesItem>> get() = _newsList

    init {
        viewModelScope.launch {
            _newsList.value = newsRepository.getNews()
        }
    }
//    fun getNews() = newsRepository.getNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()
}


