package com.example.standardblognote.model

data class ResponseCModel<T>(
    val msg: Int,
    val data: T
)