package com.example.standardblognote.data

data class ChatState(
    val isEnteringToken:Boolean = true,
    val remoteToken : String = "",
    val messageText:String  = ""
)
