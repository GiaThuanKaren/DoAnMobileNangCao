package com.example.standardblognote.data

import retrofit2.http.Body
import retrofit2.http.POST

interface FCmApi {

    @POST("/send")
    suspend fun  sendMessage(
        @Body body : SendMessageDto
    )
    @POST("/broadcast")
    suspend fun broadcast(
        @Body body :SendMessageDto
    )
}