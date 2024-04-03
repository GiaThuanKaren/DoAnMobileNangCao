package com.example.standardblognote.network

import com.example.standardblognote.model.DocumentModel
import com.example.standardblognote.model.DocumentResponseModel
import com.example.standardblognote.model.ResponseAModel
import com.example.standardblognote.model.ResponseModel
import com.example.standardblognote.model.UpdateDocumentModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @GET("/post")
    suspend fun GetAllDocument(): Response<ResponseModel<DocumentResponseModel>>

    @GET("/post/{id}")
    suspend fun GetDocumentById(@Path("id") id: String): Response<ResponseAModel<DocumentResponseModel>>

    @DELETE("/post/{id}")
    suspend fun DeleteDocumentById(@Path("id") id: String): Response<ResponseAModel<Any>>

    @PATCH("/post/{id}")
    suspend fun UpdateDocument(@Body document: UpdateDocumentModel): Response<UpdateDocumentModel>

    @GET("/post/parentPost/{id}")
    suspend fun  GetAllParentDocumentId(@Path("id") id: String): Response<ResponseModel<DocumentResponseModel>>

    @POST("/post")
    suspend fun CreateNewDocument(@Body document: DocumentModel): Response<DocumentModel>
}