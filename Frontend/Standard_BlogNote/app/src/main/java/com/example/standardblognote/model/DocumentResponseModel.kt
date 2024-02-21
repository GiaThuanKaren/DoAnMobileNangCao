package com.example.standardblognote.model

data class DocumentResponseModel(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val icon: String = "",
    val coverImageLink: String = "",
    val parentId: String? = ""
)