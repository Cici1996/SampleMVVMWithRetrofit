package com.example.samplemvvmwithretrofit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.samplemvvmwithretrofit.R
import com.example.samplemvvmwithretrofit.app.MyApplication
import com.example.samplemvvmwithretrofit.model.BaseItem
import com.example.samplemvvmwithretrofit.repository.AppRepository
import com.example.samplemvvmwithretrofit.utils.Helpers.hasInternetConnection
import com.example.samplemvvmwithretrofit.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class NewsViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    val newsData: MutableLiveData<Resource<BaseItem>> = MutableLiveData()

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        fetchNews()
    }

    private suspend fun fetchNews() {
        newsData.postValue(Resource.Loading())
        try {
            if(hasInternetConnection((getApplication<MyApplication>()))){
                val response = appRepository.getNews()
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        newsData.postValue(Resource.Success(resultResponse));
                    }
                }
            }else{
                newsData.postValue(Resource.Error("No Internet"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> newsData.postValue(
                    Resource.Error("Network Failure")
                )
                else -> newsData.postValue(
                    Resource.Error("conversion_error")
                )
            }
        }
    }

}