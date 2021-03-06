package com.example.samplemvvmwithretrofit.model

import com.google.gson.annotations.SerializedName

data class BaseMovieResponse(
    val page : Int,
    val results : ArrayList<BaseMovieItemResponse>,
)

data class BaseMovieItemResponse(
    @SerializedName("poster_path")
    val backdrop_path : String,
    val id : Int,
    val title :String,
    @SerializedName("vote_average")
    val rate : Double
)