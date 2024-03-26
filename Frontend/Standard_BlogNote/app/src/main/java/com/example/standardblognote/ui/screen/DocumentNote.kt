package com.example.standardblognote.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.standardblognote.R
import com.example.standardblognote.ui.Components.Editor
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorColors
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DocumentNote(documentId: String, navController: NavController) {
    val richTextState = rememberRichTextState()
    var unTitledState by rememberSaveable {
        mutableStateOf("")
    }
    var noteState by rememberSaveable {
        mutableStateOf("")
    }
    // Editor above Keyboard
    val isImeVisible = WindowInsets.isImeVisible
    var showEditor by remember {
        mutableStateOf(false)
    }
    val density = LocalDensity.current
    val offsetY = WindowInsets.ime.getBottom(density)
//    var previousOffset by remember {
//        mutableStateOf(0)
//    }
//    val isKeyboardGoingDown by remember(offsetY) {
//        derivedStateOf {
//            val isGoingDown = previousOffset - offsetY > 0
//            previousOffset = offsetY
//            isGoingDown
//        }
//    }
//    LaunchedEffect(key1 = isImeVisible, key2 = isKeyboardGoingDown) {
//        if (isImeVisible && !isKeyboardGoingDown) {
//            showEditor = true
//        } else {
//            showEditor = false
//        }
//    }

    Log.i("Open Document", "with Id = ${documentId}")

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = unTitledState,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = Color(55, 53, 47),
                cursorColor = Color(55, 53, 47),
            ),
            placeholder = {
                Text(
                    text = "Untitled",
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.inter_bold, FontWeight.W500))
                )},
            onValueChange = {
                unTitledState = it
            },
            textStyle = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily(Font(R.font.inter_bold, FontWeight.W500))
            )
        )
//        OutlinedTextField(
//            value = noteState,
//            onValueChange = {
//                noteState = it
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(),
//            textStyle = TextStyle(
//                fontSize = 15.sp,
//                fontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.W500))
//            ),
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                backgroundColor = Color.Transparent,
//                focusedBorderColor  = Color.Transparent,
//                disabledBorderColor = Color.Transparent,
//                unfocusedBorderColor  = Color.Transparent,
//                textColor = Color(55, 53, 47),
//                cursorColor = Color(55, 53, 47),
//            )
//        )
//        Text(
//            "Ime visible: ${WindowInsets.isImeVisible}" +
//                    "ime bottom: ${WindowInsets.ime.getBottom(density)}\n"
//        )
        RichTextEditor(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            colors = RichTextEditorDefaults.richTextEditorColors(
                containerColor = Color.White,
                textColor = Color(55, 53, 47),
                cursorColor = Color(55, 53, 47)
            ),
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.inter, FontWeight.W500))
            ),
            state = richTextState
        )

//        if (showEditor && !isKeyboardGoingDown && offsetY != 0) {
//            Editor(modifier = Modifier
//                .offset {
//                    IntOffset(0, -offsetY)
//                }
//                .fillMaxWidth()
//                .height(150.dp)
//                .border(1.dp, Color.Red)
//            )
//        }
    }
}