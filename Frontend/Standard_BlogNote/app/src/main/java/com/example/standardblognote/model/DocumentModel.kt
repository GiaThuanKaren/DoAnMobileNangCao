package com.example.standardblognote.model

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import javax.annotation.concurrent.Immutable

data class DocumentModel(
    val title: String,
    val description: String,
    val icon: String,
    val coverImagelink: String,
    val parentId: String?,
    val idUser: String
)

@Immutable
data class TextSizeModel(
    val label : String = "16.sp",
    val size : TextUnit = 16.sp
)
@Immutable
data class FontFamilyModel(
    val label : String = "Default",
    val fontFamily: FontFamily = FontFamily.Default
)