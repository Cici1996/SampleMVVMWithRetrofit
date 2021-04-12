package com.example.samplemvvmwithretrofit.model

import java.util.*

data class DataUserRequest(
    val name:String,
    val job:String
)

data class DataUserResponse(
    val name:String,
    val job:String,
    val id:String,
    val createdAt:Date
)
