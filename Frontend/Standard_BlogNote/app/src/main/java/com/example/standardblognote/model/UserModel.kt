package com.example.standardblognote.model

class UserModel (
    val id: String?,
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val authType: Int
)