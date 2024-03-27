package com.example.standardblognote.model

data class ResponseAModel<T>(
    val msg: String,
    val data: T
)
