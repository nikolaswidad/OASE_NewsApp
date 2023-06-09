package com.nikolaswidad.oasesimpleapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikolaswidad.oasesimpleapp.data.Injection
import com.nikolaswidad.oasesimpleapp.data.NewsRepository
import com.nikolaswidad.oasesimpleapp.ui.screen.news.NewsScreenViewModel

class ViewModelFactory private constructor(private val newsRepository: NewsRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository) as T
        } else if (modelClass.isAssignableFrom(NewsScreenViewModel::class.java)) {
            return NewsScreenViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}
