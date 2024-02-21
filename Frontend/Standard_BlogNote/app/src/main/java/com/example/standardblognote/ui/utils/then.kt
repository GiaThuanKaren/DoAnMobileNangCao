package com.example.standardblognote.ui.utils

infix fun <T> Boolean.then(param: T): T? = if (this) param else null