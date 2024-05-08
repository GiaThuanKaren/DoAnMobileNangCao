package com.example.standardblognote.model
import com.google.gson.annotations.SerializedName

class ProfileDetail (
    val id: String = "",
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val createdAt: String = "",
    val updatedAt: String = ""
)

data class UserProfileDetail(
    @SerializedName("msg") val msg: Int,
    @SerializedName("data") val data: ProfileDetail
)
