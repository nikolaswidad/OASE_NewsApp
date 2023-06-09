package com.nikolaswidad.oasesimpleapp.ui.screen.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.nikolaswidad.oasesimpleapp.data.NewsRepository
import com.nikolaswidad.oasesimpleapp.data.local.entity.NewsEntity
import kotlinx.coroutines.launch

class NewsScreenViewModel(private val newsRepository: NewsRepository): ViewModel() {
    private val newsData = MutableLiveData<NewsEntity>()

    fun setNewsData(news: NewsEntity) {
        newsData.value = news
    }

    val bookmarkStatus = newsData.switchMap {
        newsRepository.isNewsBookmarked(it.title)
    }

    fun changeBookmark(newsDetail: NewsEntity) {
        viewModelScope.launch {
            if (bookmarkStatus.value as Boolean) {
                newsRepository.deleteNews(newsDetail.title)
            } else {
                newsRepository.saveNews(newsDetail)
            }
        }
    }
}