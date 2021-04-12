package com.example.samplemvvmwithretrofit.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvmwithretrofit.repository.AppRepository

class ViewModelProviderFactory(
    val app: Application,
    val appRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(app, appRepository) as T
        }
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(app, appRepository) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}