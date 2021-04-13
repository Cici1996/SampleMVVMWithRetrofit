package com.example.samplemvvmwithretrofit.network

import com.example.samplemvvmwithretrofit.model.*
import com.example.samplemvvmwithretrofit.utils.Constants.TMDB_API_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface API {
    @GET("top-headlines?country=id&category=business&apiKey=1631d6a42c044c5e82d81ee63605ab1b")
    suspend fun getNews(): Response<BaseItem>

    @GET("movie/upcoming?api_key="+TMDB_API_KEY+"&language=en-US")
    suspend fun getMoviesToprated(
        @Query("page") page:Int
    ): Response<BaseMovieResponse>

    @GET("movie/upcoming?api_key="+TMDB_API_KEY+"&language=en-US")
    suspend fun getMoviesTopratedOther(
        @Query("page") page:Int
    ): Response<BaseMovieResponseOther>

    @POST("users")
    suspend fun saveUser(
        @Body user: DataUserRequest
    ): Response<DataUserResponse>
}