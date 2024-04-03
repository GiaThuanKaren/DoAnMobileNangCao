package com.example.standardblognote.model


data class GetUserModel(
    val id: String,
    val username: String,
    val password: String,
    val createdAt: String,
    val updatedAt: String
)

data class UserResponse(
    val data: List<GetUserModel>
)