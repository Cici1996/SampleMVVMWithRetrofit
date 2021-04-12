package com.example.samplemvvmwithretrofit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.samplemvvmwithretrofit.R
import com.example.samplemvvmwithretrofit.app.MyApplication
import com.example.samplemvvmwithretrofit.model.DataUserRequest
import com.example.samplemvvmwithretrofit.model.DataUserResponse
import com.example.samplemvvmwithretrofit.repository.AppRepository
import com.example.samplemvvmwithretrofit.utils.Event
import com.example.samplemvvmwithretrofit.utils.Helpers.hasInternetConnection
import com.example.samplemvvmwithretrofit.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class UserViewModel(
    app: Application,
    private val appRepository: AppRepository
): AndroidViewModel(app) {

    private val _loginResponse = MutableLiveData<Event<Resource<DataUserResponse>>>()
    val loginResponse: LiveData<Event<Resource<DataUserResponse>>> = _loginResponse

    fun loginUser(body: DataUserRequest) = viewModelScope.launch {
        login(body)
    }

    private suspend fun login(body: DataUserRequest) {
        _loginResponse.postValue(Event(Resource.Loading()))
        try {
            if (hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.login(body)
                _loginResponse.postValue(handleResponse(response))
            } else {
                _loginResponse.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _loginResponse.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _loginResponse.postValue(
                        Event(Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.conversion_error
                            )
                        ))
                    )
                }
            }
        }
    }

    private fun handleResponse(response: Response<DataUserResponse>): Event<Resource<DataUserResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

}