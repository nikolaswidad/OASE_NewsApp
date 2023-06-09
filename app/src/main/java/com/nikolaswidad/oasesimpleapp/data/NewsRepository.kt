package com.nikolaswidad.oasesimpleapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.nikolaswidad.oasesimpleapp.BuildConfig
import com.nikolaswidad.oasesimpleapp.data.local.entity.NewsEntity
import com.nikolaswidad.oasesimpleapp.data.local.room.NewsDao
import com.nikolaswidad.oasesimpleapp.data.remote.response.ArticlesItem
import com.nikolaswidad.oasesimpleapp.data.remote.response.Source
//import com.nikolaswidad.oasesimpleapp.data.local.room.NewsDao
import com.nikolaswidad.oasesimpleapp.data.remote.retrofit.ApiService

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao
){
    suspend fun getNews() : List<ArticlesItem> {
        val response = apiService.getNews("us", BuildConfig.API_KEY)
        return response.article
    }
//    fun getNews(): LiveData<Result<List<NewsEntity>>> = liveData{
//        emit(Result.Loading)
//        try {
//            val response = apiService.getNews("us", BuildConfig.API_KEY)
//            val articles = response.article
//            val newsList = articles.map { article ->
//                NewsEntity(
//                    article.title,
//                    article.publishedAt,
//                    article.urlToImage,
//                    article.url,
//                    )
//            }
//            emit(Result.Success(newsList))
//        } catch (e: Exception){
//            Log.d("NewsRepository", "getNews: ${e.message.toString()} ")
//            emit(Result.Error(e.message.toString()))
//        }
//    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    suspend fun saveNews(news: NewsEntity) {
        newsDao.saveNews(news)
    }

    suspend fun deleteNews(title: String) {
        newsDao.deleteNews(title)
    }

    fun isNewsBookmarked(title: String): LiveData<Boolean> {
        return newsDao.isNewsBookmarked(title)
    }


    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao
        ) : NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao)
            }.also { instance = it }
    }
}