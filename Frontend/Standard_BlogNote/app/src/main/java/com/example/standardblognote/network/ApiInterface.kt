package com.example.standardblognote.network

import com.example.standardblognote.model.DocumentCModel
import com.example.standardblognote.model.DocumentModel
import com.example.standardblognote.model.DocumentResponseModel
<<<<<<< HEAD
=======
import com.example.standardblognote.model.DocumentUserModel
import com.example.standardblognote.model.GetUserModel
>>>>>>> 8a10fbedf4271859838c55c89e5f6d92fcc504da
import com.example.standardblognote.model.ResponseAModel
import com.example.standardblognote.model.ResponseCModel
import com.example.standardblognote.model.ResponseModel
<<<<<<< HEAD
=======
import com.example.standardblognote.model.Token
import com.example.standardblognote.model.UserModel
import com.example.standardblognote.model.UserResponse
>>>>>>> 8a10fbedf4271859838c55c89e5f6d92fcc504da
import com.example.standardblognote.model.UpdateDocumentModel
import com.example.standardblognote.model.UserModel
import com.example.standardblognote.model.UserProfileDetail
import com.example.standardblognote.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {

    @GET("/post")
    suspend fun GetAllDocument(): Response<ResponseModel<DocumentResponseModel>>

    @GET("/post/{id}")
    suspend fun GetDocumentById(@Path("id") id: String): Response<ResponseAModel<DocumentResponseModel>>

    @DELETE("/post/{id}")
    suspend fun DeleteDocumentById(@Path("id") id: String): Response<ResponseAModel<Any>>

    @PUT("/post/update/{id}")
    suspend fun UpdateDocument(@Path("id") id: String, @Body document: UpdateDocumentModel): Response<Any>

    // Done
    @GET("/user-post/getAllPostByIdUser/{idUser}/{idParentPost}")
    suspend fun  GetAllParentDocumentId(@Path("idUser") idUser: String, @Path("idParentPost") idParentPost: String): Response<List<DocumentUserModel>>

    @POST("/post")
    suspend fun CreateNewDocument(@Body document: DocumentModel): Response<ResponseCModel<DocumentCModel>>

    @POST("/users")
    suspend fun CreateNewUser(@Body user: UserModel): Response<UserModel>
<<<<<<< HEAD
    @GET("/users/{id}")
    suspend fun getUserProfile(@Path("id") id: String): Response<UserProfileDetail>

=======
>>>>>>> 8a10fbedf4271859838c55c89e5f6d92fcc504da

    @GET("/users")
    suspend fun getUsers(): Response<UserResponse>

    @POST("/notification")
    suspend fun UpdateToken(@Body token: Token): Response<Any>
}