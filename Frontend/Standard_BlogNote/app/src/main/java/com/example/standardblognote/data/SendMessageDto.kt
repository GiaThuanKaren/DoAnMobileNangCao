package com.example.standardblognote.data

data class SendMessageDto(
    val to:String?,
    val nofication:NofiticationBody
)


data class NofiticationBody(
    val title:String,
    val body:String
)