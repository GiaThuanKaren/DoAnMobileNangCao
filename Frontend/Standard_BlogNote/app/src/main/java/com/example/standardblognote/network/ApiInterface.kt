package com.example.standardblognote.network

import com.example.standardblognote.model.DocumentResponseModel
import com.example.standardblognote.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/post")
    suspend fun GetAllDocument(): Response<ResponseModel<DocumentResponseModel>>

    @GET("/post/parentPost/{id}")
    suspend fun  GetAllParentDocumentId(@Path("id") id: String): Response<ResponseModel<DocumentResponseModel>>
}