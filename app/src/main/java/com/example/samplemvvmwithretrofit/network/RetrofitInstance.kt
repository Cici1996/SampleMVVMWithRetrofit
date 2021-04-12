package com.example.samplemvvmwithretrofit.network

import com.example.samplemvvmwithretrofit.utils.Constants.BASE_URL
import com.example.samplemvvmwithretrofit.utils.Constants.BASE_URL_REQRES
import com.example.samplemvvmwithretrofit.utils.Constants.BASE_URL_TMDB
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {

        private val retrofitNews by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        private val retrofitTmdb by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL_TMDB)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        private val retrofitReqres by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL_REQRES)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }


        val newsApi by lazy {
            retrofitNews.create(API::class.java)
        }

        val tmdbApi by lazy {
            retrofitTmdb.create(API::class.java)
        }

        val reqresApi by lazy {
            retrofitReqres.create(API::class.java)
        }
    }
}