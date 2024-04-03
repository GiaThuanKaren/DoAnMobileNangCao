package com.example.standardblognote.model

data class UpdateDocumentModel(
    val title: String,
    val description: String,
    val icon: String,
    val coverImagelink: String,
    val parentId: String,
)
