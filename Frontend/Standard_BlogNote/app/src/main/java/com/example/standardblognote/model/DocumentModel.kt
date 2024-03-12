package com.example.standardblognote.model

data class DocumentModel(
    val title: String,
    val description: String,
    val icon: String,
    val coverImagelink: String,
    val parentId: Int,
    val idUser: Int
)