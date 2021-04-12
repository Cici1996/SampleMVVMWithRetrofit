package com.example.samplemvvmwithretrofit.model

data class BaseItem (
    val status : String,
    val totalResults : Int,
    val articles : ArrayList<ItemNewsModel>
)

data class ItemNewsModel (
    val author : String,
    val title : String
)