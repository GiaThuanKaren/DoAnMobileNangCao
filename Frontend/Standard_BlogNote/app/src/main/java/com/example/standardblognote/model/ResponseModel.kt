package com.example.standardblognote.model

data class ResponseModel<T>(
    val msg: String,
    val data: List<T>
)
