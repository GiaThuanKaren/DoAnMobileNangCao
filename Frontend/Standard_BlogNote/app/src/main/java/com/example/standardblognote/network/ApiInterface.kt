package com.example.standardblognote.network

import com.example.standardblognote.model.DocumentCModel
import com.example.standardblognote.model.DocumentModel
import com.example.standardblognote.model.DocumentResponseModel
import com.example.standardblognote.model.GetUserModel
import com.example.standardblognote.model.ResponseAModel
import com.example.standardblognote.model.ResponseCModel
import com.example.standardblognote.model.ResponseModel
import com.example.standardblognote.model.UserModel
import com.example.standardblognote.model.UserResponse
import com.example.standardblognote.model.UpdateDocumentModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("/post")
    suspend fun GetAllDocument(): Response<ResponseModel<DocumentResponseModel>>

    @GET("/post/{id}")
    suspend fun GetDocumentById(@Path("id") id: String): Response<ResponseAModel<DocumentResponseModel>>

    @DELETE("/post/{id}")
    suspend fun DeleteDocumentById(@Path("id") id: String): Response<ResponseAModel<Any>>

    @PUT("/post/update/{id}")
    suspend fun UpdateDocument(@Path("id") id: String, @Body document: UpdateDocumentModel): Response<Any>

    @GET("/post/parentPost/{id}")
    suspend fun  GetAllParentDocumentId(@Path("id") id: String): Response<ResponseModel<DocumentResponseModel>>

    @POST("/post")
    suspend fun CreateNewDocument(@Body document: DocumentModel): Response<ResponseCModel<DocumentCModel>>

    @POST("/users")
    suspend fun CreateNewUser(@Body user: UserModel): Response<UserModel>

    @GET("/users")
    suspend fun getUsers(): Response<UserResponse>
}