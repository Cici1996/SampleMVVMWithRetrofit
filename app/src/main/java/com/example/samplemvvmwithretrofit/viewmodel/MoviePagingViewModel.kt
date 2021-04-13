package com.example.samplemvvmwithretrofit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.samplemvvmwithretrofit.R
import com.example.samplemvvmwithretrofit.app.MyApplication
import com.example.samplemvvmwithretrofit.model.BaseMovieResponseOther
import com.example.samplemvvmwithretrofit.repository.AppRepository
import com.example.samplemvvmwithretrofit.utils.Helpers
import com.example.samplemvvmwithretrofit.utils.Resource
import kotlinx.coroutines.launch
import java.io.IOException

class MoviePagingViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    val data: MutableLiveData<Resource<BaseMovieResponseOther>> = MutableLiveData()
    var dataRespon:BaseMovieResponseOther? = null

    fun getData(page:Int) = viewModelScope.launch {
        fetchData(page)
    }

    private suspend fun fetchData(page:Int) {
        data.postValue(Resource.Loading())
        try {
            if(Helpers.hasInternetConnection((getApplication<MyApplication>()))){
                val response = appRepository.getMoviesTopRatedOther(page)
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        if(dataRespon == null){
                            dataRespon = resultResponse
                        }else{
                            val oldArticles = dataRespon?.results
                            val newArticles = resultResponse.results
                            oldArticles?.addAll(newArticles)
                        }
                        var responseData = dataRespon ?:resultResponse
                        data.postValue(Resource.Success(responseData));
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