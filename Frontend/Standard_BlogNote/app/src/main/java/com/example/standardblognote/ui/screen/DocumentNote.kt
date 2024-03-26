package com.example.standardblognote.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.standardblognote.R
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.model.DocumentResponseModel
import com.example.standardblognote.network.RetrofitInstance
import com.example.standardblognote.ui.Components.Editor
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorColors
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@Composable
fun DocumentNote(documentId: String, navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val state = rememberRichTextState()
    var unTitledState by rememberSaveable {
        mutableStateOf("")
    }
    var apiDocuments by remember {
        mutableStateOf(DocumentResponseModel())
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch(Dispatchers.IO) {
            val res = try {
                RetrofitInstance.api.GetDocumentById(documentId)
            } catch (e: HttpException) {
                Log.i("INFO Api Call Fail", "${e.message()}")
                return@launch
            } catch (e: IOException) {
                Log.i("INFO Api Call Fail", "${e.message}")
                return@launch
            }

            if (res.isSuccessful && res.body() != null) {
                withContext(Dispatchers.Main) {
                    val response = res.body()!!
//                            && response.msg == 200
                    if (response != null) {
//                        apiDocuments = response.data.get(0)
                        unTitledState = response.data.title
                        Log.i("STANDARDs", "${response.data.title}")
                    }

                }
            }
        }
    }

    Log.i("Open Document", "with Id = ${documentId}")

    Scaffold(
        topBar = { TopAppBar(unTitledState) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        ) {
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

                RichTextEditor(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = RichTextEditorDefaults.richTextEditorColors(
                        containerColor = Color.White,
                        textColor = Color(55, 53, 47),
                        cursorColor = Color(55, 53, 47)
                    ),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.inter, FontWeight.W500))
                    ),
                    state = state
                )
                Editor(
                    modifier = Modifier.navigationBarsPadding(),
                    state = state,
                    onBoldClick = {
                        state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    },
                    onItalicClick = {
                        state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                    },
                    onUnderlineClick = {
                        state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                    },
                    onTitleClick = {
                    },
                    onSubtitleClick = {
                    },
                    onTextColorClick = {
                        state.toggleSpanStyle(SpanStyle(color = Color.Red))
                    },
                    onStartAlignClick = {
                    },
                    onEndAlignClick = {
                    },
                    onCenterAlignClick = {
                    },
                    onExportClick = {
                        Log.d("Editor", state.toMarkdown())
                    }
                )
            }
        }
    }

}

@Composable
fun TopAppBar(title: String) {
    Column {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.chevronleft), contentDescription = "Back")
                Spacer(modifier = Modifier.width(10.dp))
                Image(painter = painterResource(id = R.drawable.file), contentDescription = "File")
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = if (title.isNotEmpty()) title else "Untitled",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.W500)),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = "Share",
                    modifier = Modifier.padding(end = 13.dp)
                )
                Image(painter = painterResource(id = R.drawable.comment),
                    contentDescription = "Comment",
                    modifier = Modifier.padding(end = 13.dp)
                )
                Image(painter = painterResource(id = R.drawable.more),
                    contentDescription = "More"
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Divider()
    }
}