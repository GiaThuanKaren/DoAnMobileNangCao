package com.example.standardblognote.model

import java.time.LocalDateTime

data class DocumentUserModel(
    val user_id: String = "",
    val post_id: String = "",
    val created_at: Any, //LocalDateTime
    val listPost: DocumentResponse2Model
)