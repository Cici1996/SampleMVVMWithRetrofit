package com.example.samplemvvmwithretrofit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.samplemvvmwithretrofit.R
import com.example.samplemvvmwithretrofit.app.MyApplication
import com.example.samplemvvmwithretrofit.model.BaseMovieResponse
import com.example.samplemvvmwithretrofit.repository.AppRepository
import com.example.samplemvvmwithretrofit.utils.Helpers
import com.example.samplemvvmwithretrofit.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class MovieViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    val data: MutableLiveData<Resource<BaseMovieResponse>> = MutableLiveData()

    init {
        getData()
    }

    fun getData() = viewModelScope.launch {
        fetchData()
    }

    private suspend fun fetchData() {
        data.postValue(Resource.Loading())
        try {
            if(Helpers.hasInternetConnection((getApplication<MyApplication>()))){
                val response = appRepository.getMoviesTopRated(1)
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        data.postValue(Resource.Success(resultResponse));
                    }
                }
            }else{
                data.postValue(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet)))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> data.postValue(
                    Resource.Error(getApplication<MyApplication>().getString(R.string.network_failure))
                )
                else -> data.postValue(
                    Resource.Error(getApplication<MyApplication>().getString(R.string.conversion_error))
                )
            }
        }
    }
}