package com.example.samplemvvmwithretrofit.repository

import com.example.samplemvvmwithretrofit.model.DataUserRequest
import com.example.samplemvvmwithretrofit.network.RetrofitInstance

class AppRepository {
    suspend fun getNews() = RetrofitInstance.newsApi.getNews()

    suspend fun getMoviesTopRated(page:Int) = RetrofitInstance.tmdbApi.getMoviesToprated(page)

    suspend fun login(dataLogin:DataUserRequest) = RetrofitInstance.reqresApi.saveUser(dataLogin)
}