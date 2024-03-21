package com.example.standardblognote.model

class UserModel (
    val username: String,
    val password: String? = null,
    val confirmPassword: String? = null,
    val authType: Int
)