package com.example.standardblognote.model

data class DocumentResponse2Model(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val coverImagelink: String,
    val parentId: String?,
    val idUser: Int
)
