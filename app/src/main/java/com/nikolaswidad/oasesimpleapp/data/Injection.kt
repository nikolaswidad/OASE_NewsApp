package com.nikolaswidad.oasesimpleapp.data

import android.content.Context
import com.nikolaswidad.oasesimpleapp.data.local.room.NewsDatabase
import com.nikolaswidad.oasesimpleapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context) : NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}