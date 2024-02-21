package com.example.standardblognote.model

data class ResponseModel<T>(
    val msg: Int,
    val data: List<T>
)
