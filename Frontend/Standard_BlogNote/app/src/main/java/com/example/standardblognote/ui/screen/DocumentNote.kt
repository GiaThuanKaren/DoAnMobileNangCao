    package com.example.standardblognote.ui.screen
    
    import android.util.Log
    import androidx.compose.animation.AnimatedVisibility
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.border
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.interaction.MutableInteractionSource
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.ExperimentalLayoutApi
    import androidx.compose.foundation.layout.FlowRow
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxHeight
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.navigationBarsPadding
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.text.KeyboardActions
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.material.*
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.FormatAlignCenter
    import androidx.compose.material.icons.filled.FormatAlignLeft
    import androidx.compose.material.icons.filled.FormatAlignRight
    import androidx.compose.material.icons.filled.FormatBold
    import androidx.compose.material.icons.filled.FormatColorText
    import androidx.compose.material.icons.filled.FormatItalic
    import androidx.compose.material.icons.filled.FormatSize
    import androidx.compose.material.icons.filled.FormatUnderlined
    import androidx.compose.material.icons.filled.Title
    import androidx.compose.material3.DatePicker
    import androidx.compose.material3.DisplayMode
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.SheetState
    import androidx.compose.material3.SheetValue
    import androidx.compose.material3.rememberDatePickerState
    import androidx.compose.runtime.*
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.ExperimentalComposeUiApi
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalSoftwareKeyboardController
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.AnnotatedString
    import androidx.compose.ui.text.ParagraphStyle
    import androidx.compose.ui.text.SpanStyle
    import androidx.compose.ui.text.TextRange
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.buildAnnotatedString
    import androidx.compose.ui.text.font.Font
    import androidx.compose.ui.text.font.FontFamily
    import androidx.compose.ui.text.font.FontStyle
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.input.ImeAction
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.text.style.TextDecoration
    import androidx.compose.ui.text.style.TextOverflow
    import androidx.compose.ui.text.withStyle
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import com.example.standardblognote.R
    import com.example.standardblognote.data.home.HomeViewModel
    import com.example.standardblognote.model.DocumentModel
    import com.example.standardblognote.model.DocumentResponseModel
    import com.example.standardblognote.model.UpdateDocumentModel
    import com.example.standardblognote.network.RetrofitInstance
    import com.example.standardblognote.ui.Components.RichText.ColorPickerDialog
    import com.example.standardblognote.ui.Components.RichText.FontDropDown
    import com.example.standardblognote.ui.Components.RichText.RichTextToolBox
    import com.example.standardblognote.ui.Components.RichText.UnitDropDown
    import com.example.standardblognote.ui.theme.textColorPrimary
    import com.example.standardblognote.ui.theme.textColorSecondary
    import com.mohamedrejeb.richeditor.model.RichTextState
    import com.mohamedrejeb.richeditor.model.rememberRichTextState
    import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
    import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
    import com.example.standardblognote.ui.utils.Constants
    import com.example.standardblognote.utils.useDebounce
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.Job
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext
    import me.saket.extendedspans.ExtendedSpans
    import me.saket.extendedspans.RoundedCornerSpanPainter
    import me.saket.extendedspans.SquigglyUnderlineSpanPainter
    import me.saket.extendedspans.drawBehind
    import me.saket.extendedspans.rememberSquigglyUnderlineAnimator
    import retrofit2.HttpException
    import java.io.IOException
    
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DocumentNote(documentId: String, parentDocumentId: String?, navController: NavController, homeViewModel: HomeViewModel) {
        val contentState = rememberRichTextState()
        val titleSize = MaterialTheme.typography.displaySmall.fontSize
        val subtitleSize = MaterialTheme.typography.titleLarge.fontSize
        contentState.setConfig(
            linkColor = Color.Blue,
            linkTextDecoration = TextDecoration.Underline,
            codeColor = Color.Cyan,
            codeBackgroundColor = Color.Black,
            codeStrokeColor = Color.Transparent,
        )
    
        val sheetIsOpen = remember {
            mutableStateOf(false)
        }
        val sheetState = androidx.compose.material3.rememberBottomSheetScaffoldState(
            bottomSheetState = SheetState(
                confirmValueChange = {
                    sheetIsOpen.value = it == SheetValue.Expanded

                    true
                },
                skipPartiallyExpanded = false,
                initialValue = when (sheetIsOpen.value) {
                    false -> SheetValue.Hidden
                    true -> SheetValue.Expanded
                }
            )
        )
        val contentSource = remember {
            MutableInteractionSource()
        }
        var unTitledState by rememberSaveable {
            mutableStateOf("")
        }
        var descriptionState by remember {
            mutableStateOf("")
        }

        var isDocumentLoaded by remember {
             mutableStateOf(false)
        }

        val coroutineScope1 = rememberCoroutineScope()
        suspend fun UpdateDocument() {
            Log.i("Untitled", unTitledState)
            Log.i("Description", descriptionState)
            val document = UpdateDocumentModel(unTitledState, descriptionState, "", "")
            val res = try {
                RetrofitInstance.api.UpdateDocument(documentId, document)
            } catch (e: HttpException) {
                Log.i("INFO Api Call Fail", "${e.message()}")
                return
            } catch (e: IOException) {
                Log.i("INFO Api Call Fail", "${e.message}")
                return
            }
            if (res.isSuccessful && res.body() != null) {
                Log.i("Update", "${res.body()}")
                Log.i("Update", "Document updated")
            }
        }
    
        LaunchedEffect(unTitledState, descriptionState) {
            Log.i("StateChange", "Changed")
            if (isDocumentLoaded) {
                coroutineScope1.launch(Dispatchers.IO) {
                    UpdateDocument()
                }
            }
        }
    
        LaunchedEffect(contentState.annotatedString) {
            // TODO This code will run each time the rich text is changed.
//            descriptionState = contentState.annotatedString.toString()
            descriptionState = contentState.toMarkdown()
        }
    
        val coroutineScope = rememberCoroutineScope()
        suspend fun DeleteDocumentById() {
            val res = try {
                RetrofitInstance.api.DeleteDocumentById(documentId)
            } catch (e: HttpException) {
                Log.i("INFO Api Call Fail", "${e.message()}")
                return
            } catch (e: IOException) {
                Log.i("INFO Api Call Fail", "${e.message}")
                return
            }
        }

        // TODO -> GET DOCUMENT
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
                        Log.i("INFO Api Call Success", "Call Success")
                        //                            && response.msg == 200
                        if (response != null) {
                            //                        apiDocuments = response.data.get(0)
                            unTitledState = response.data.title
                            //                        descriptionState = response.data.description
                            contentState.setMarkdown(response.data.description)
                        }
                    }
                }
                isDocumentLoaded = true
            }
        }
    
        Scaffold(
            topBar = { TopAppBar(unTitledState, navController) {
                coroutineScope.launch {
                    DeleteDocumentById()
                }
            } },
            bottomBar = {
                val isColorPopupExpanded = remember {
                    mutableStateOf(false)
                }
                val isUnitDropDownExpanded = remember {
                    mutableStateOf(false)
                }
                val unitDropDownType = remember {
                    mutableStateOf("")
                }
                val isFontDropDownExpanded = remember {
                    mutableStateOf(false)
                }
                val colorPopupType = remember {
                    mutableStateOf("")
                }

                if (sheetState.bottomSheetState.currentValue != SheetValue.Expanded) {
                    Box {
                        RichTextToolBox(contentState,
                            onBoldClick = {
                                contentState.toggleSpanStyle(
                                    SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                    )
                                )
                            },
                            onItalicClick = {
                                contentState.toggleSpanStyle(
                                    SpanStyle(
                                        fontStyle = FontStyle.Italic
                                    )
                                )
                            },
                            onUnderlineClick = {
                                contentState.toggleSpanStyle(
                                    SpanStyle(
                                        textDecoration = TextDecoration.Underline
                                    )
                                )

                            },
                            onLineThroughClick = {
                                contentState.toggleSpanStyle(
                                    SpanStyle(
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                )
                            },
                            onTextSizeClick = {
                                if (isUnitDropDownExpanded.value) {
                                    isUnitDropDownExpanded.value = false
                                    colorPopupType.value = ""
                                } else {
                                    isUnitDropDownExpanded.value = true
                                    unitDropDownType.value = "textSize"
                                }
                            },
                            onTextColorClick = {
                                isUnitDropDownExpanded.value = false
                                isColorPopupExpanded.value = true
                                colorPopupType.value = "textColor"
                            },
                            onTextBgClick = {
                                isUnitDropDownExpanded.value = false
                                isColorPopupExpanded.value = true
                                colorPopupType.value = "bgColor"
                            },
                            onTextLeftClick = {
                                contentState
                                    .toggleParagraphStyle(
                                        ParagraphStyle(
                                            textAlign = TextAlign.Left
                                        )
                                    )
                            },
                            onTextCenterClick = {
                                contentState
                                    .toggleParagraphStyle(
                                        ParagraphStyle(
                                            textAlign = TextAlign.Center
                                        )
                                    )
                            },
                            onTextRightClick = {
                                val cursorPos = contentState.selection.end
                                contentState
                                    .toggleParagraphStyle(
                                        ParagraphStyle(
                                            textAlign = TextAlign.Right
                                        )
                                    )
                                contentState.selection = TextRange(cursorPos)
                            },
                            onUnOrderedListClick = {
                                contentState.toggleUnorderedList()
                            },
                            onOrderedListClick = {
                                contentState.toggleOrderedList()
                            },
                            onCodeClick = {
                                contentState.addCodeSpan()
                            },
                            onLetterSpacingClick = {
                                if (isUnitDropDownExpanded.value) {
                                    isUnitDropDownExpanded.value = false
                                    colorPopupType.value = ""
                                } else {
                                    isUnitDropDownExpanded.value = true
                                    unitDropDownType.value = "letterSpacing"
                                }
                            },
                            onLineHeightClick = {
                                if (isUnitDropDownExpanded.value) {
                                    isUnitDropDownExpanded.value = false
                                    colorPopupType.value = ""
                                } else {
                                    isUnitDropDownExpanded.value = true
                                    unitDropDownType.value = "lineHeight"
                                }
                            })

                        if (isFontDropDownExpanded.value) {
                            Column(
                                Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                FontDropDown(
                                    selectedValue = contentState.currentSpanStyle.fontFamily
                                        ?: FontFamily.Default,
                                    listItems = Constants.fontsList,
                                    onClosed = {
                                        isFontDropDownExpanded.value = false
                                    },
                                    onSelected = {
                                        contentState.toggleSpanStyle(
                                            SpanStyle(
                                                fontFamily = it
                                            )
                                        )
                                    }
                                )
                            }
                        }

                        if (isUnitDropDownExpanded.value) {
                            Column(
                                Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                UnitDropDown(
                                    selectedValue = when (unitDropDownType.value) {
                                        "textSize" -> contentState.currentSpanStyle.fontSize.value.toInt()
                                        "lineHeight" -> contentState.currentParagraphStyle.lineHeight.value.toInt()
                                        "letterSpacing" -> contentState.currentSpanStyle.letterSpacing.value.toInt()
                                        else -> 0
                                    },
                                    listItems = Constants.textSizeList,
                                    onClosed = {
                                        isUnitDropDownExpanded.value = false
                                        unitDropDownType.value = ""
                                    },
                                    onSelected = {
                                        when (unitDropDownType.value) {
                                            "textSize" -> {
                                                contentState.toggleSpanStyle(
                                                    SpanStyle(
                                                        fontSize = it
                                                    )
                                                )
                                            }

                                            "lineHeight" -> {
                                                contentState.toggleParagraphStyle(
                                                    ParagraphStyle(
                                                        lineHeight = it
                                                    )
                                                )
                                            }

                                            "letterSpacing" -> {
                                                contentState.toggleSpanStyle(
                                                    SpanStyle(
                                                        letterSpacing = it
                                                    )
                                                )
                                            }
                                        }
                                    }
                                )
                            }
                        }

                        if (isColorPopupExpanded.value) {
                            Column {
                                ColorPickerDialog(
                                    onSetColor = {
                                        when (colorPopupType.value) {
                                            "textColor" -> {
                                                contentState.toggleSpanStyle(
                                                    SpanStyle(
                                                        color = it
                                                    )
                                                )
                                            }

                                            "bgColor" -> {
                                                contentState.toggleSpanStyle(
                                                    SpanStyle(
                                                        background = it
                                                    )
                                                )
                                            }
                                        }

                                        isColorPopupExpanded.value = false
                                        colorPopupType.value = ""
                                    }, onResetColor = {
                                        when (colorPopupType.value) {
                                            "textColor" -> {
                                                contentState.toggleSpanStyle(
                                                    SpanStyle(
                                                        color = Color.White
                                                    )
                                                )
                                            }

                                            "bgColor" -> {
                                                contentState.toggleSpanStyle(
                                                    SpanStyle(
                                                        background = Color.Transparent
                                                    )
                                                )
                                            }

                                        }
                                        isColorPopupExpanded.value = false
                                        colorPopupType.value = ""
                                    }, onCloseClick = {
                                        isColorPopupExpanded.value = false
                                        colorPopupType.value = ""
                                    })
                            }
                        }
                    }
                }
            }) { innerPadding ->
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
                        state = contentState,
                        shape = RoundedCornerShape(10.dp),
                        interactionSource = contentSource,
                        placeholder = {
                            androidx.compose.material3.Text(
                                "✨ Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                                fontSize = 16.sp, fontWeight = FontWeight.Normal,
                                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
                            )
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        colors = RichTextEditorDefaults.richTextEditorColors(
                            containerColor = Color.Transparent,
                            placeholderColor = textColorSecondary,
                            textColor = textColorPrimary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = textColorPrimary
                        ),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            lineHeight = 22.sp
                        ),
                    )
                }
            }
        }
    
    }
    
    @Composable
    fun TopAppBar(title: String, navController: NavController, onDelete: () -> Job) {
        Column {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.chevronleft),
                        contentDescription = "Back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
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
//                    Image(
//                        painter = painterResource(id = R.drawable.share),
//                        contentDescription = "Share",
//                        modifier = Modifier.padding(end = 13.dp)
//                    )
//                    Image(painter = painterResource(id = R.drawable.comment),
//                        contentDescription = "Comment",
//                        modifier = Modifier.padding(end = 13.dp)
//                    )
                    Image(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = "More",
                        modifier = Modifier.clickable {
                           onDelete.invoke()
                            navController.popBackStack()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Divider()
        }
    }
    

