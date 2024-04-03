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
    fun DocumentNote(documentId: String, parentDocumentId: String, navController: NavController, homeViewModel: HomeViewModel) {
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
    //        mutableStateOf(DocumentResponseModel())
            mutableStateOf("")
        }
    
        val coroutineScope1 = rememberCoroutineScope()
        suspend fun UpdateDocument() {
            val document = UpdateDocumentModel(unTitledState, descriptionState, "", "", parentDocumentId)
            val res = try {
                RetrofitInstance.api.UpdateDocument(document)
            } catch (e: HttpException) {
                Log.i("INFO Api Call Fail", "${e.message()}")
                return
            } catch (e: IOException) {
                Log.i("INFO Api Call Fail", "${e.message}")
                return
            }
            if (res.isSuccessful && res.body() != null) {
                Log.i("Update", "Document updated")
            }
        }
    
        unTitledState.useDebounce { unTitledState = it }
        descriptionState.useDebounce { descriptionState = it.toString() }
    
        LaunchedEffect(unTitledState, descriptionState) {
            Log.i("StateChange", "Changed")
            coroutineScope1.launch(Dispatchers.IO) {
                UpdateDocument()
            }
        }
    
        LaunchedEffect(contentState.annotatedString) {
            // TODO This code will run each time the rich text is changed.
            descriptionState = contentState.annotatedString.toString()
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
    //                            && response.msg == 200
                        if (response != null) {
    //                        apiDocuments = response.data.get(0)
                            unTitledState = response.data.title
    //                        descriptionState = response.data.description
                            contentState.setMarkdown(response.data.description)
                            Log.i("STANDARDs", "${response.data.title}")
                        }
                    }
                }
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
    
    //                val style = MaterialTheme.typography.headlineLarge.copy(
    //                    fontWeight = FontWeight.Normal,
    //                    fontSize = 40.sp,
    //                    lineHeight = 52.sp,
    //                )
    //                ProvideTextStyle(style) {
    //                    Column(
    //                        modifier = Modifier.padding(24.dp),
    //                        verticalArrangement = Arrangement.spacedBy(20.dp),
    //                    ) {
    //                        ExtendedSpansText(
    //                            text = buildAnnotatedString {
    //                                append("Give your ")
    //                                withStyle(SpanStyle(background = MaterialTheme.colorScheme.primaryContainer)) {
    //                                    append("heart and soul")
    //                                }
    //                                append(" to me")
    //                            }
    //                        )
    //                        ExtendedSpansText(
    //                            text = buildAnnotatedString {
    //                                append("And life will always be ")
    //                                withStyle(
    //                                    SpanStyle(
    //                                        textDecoration = TextDecoration.Underline,
    //                                        color = Color(0xFFFF3199)
    //                                    )
    //                                ) {
    //                                    append("la vie en rose")
    //                                }
    //                            }
    //                        )
    //                    }
    //                }
    
                    RichTextEditor(
                        state = contentState,
                        shape = RoundedCornerShape(10.dp),
                        interactionSource = contentSource,
                        placeholder = {
                            androidx.compose.material3.Text(
                                "Write what you want or use help of AI ✨",
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
    //
    //                RichTextEditor(
    //                    modifier = Modifier
    //                        .fillMaxWidth(),
    //                    colors = RichTextEditorDefaults.richTextEditorColors(
    //                        containerColor = Color.White,
    //                        textColor = Color(55, 53, 47),
    //                        cursorColor = Color(55, 53, 47)
    //                    ),
    //                    textStyle = TextStyle(
    //                        fontSize = 18.sp,
    //                        fontFamily = FontFamily(Font(R.font.inter, FontWeight.W500))
    //                    ),
    //                    state = state
    //                )
    //
    //                EditorControls(
    //                    modifier = Modifier.weight(2f),
    //                    state = state,
    //                    onBoldClick = {
    //                        state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
    //                    },
    //                    onItalicClick = {
    //                        state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
    //                    },
    //                    onUnderlineClick = {
    //                        state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
    //                    },
    //                    onTitleClick = {
    //                        state.toggleSpanStyle(SpanStyle(fontSize = titleSize))
    //                    },
    //                    onSubtitleClick = {
    //                        state.toggleSpanStyle(SpanStyle(fontSize = subtitleSize))
    //                    },
    //                    onTextColorClick = {
    //                        state.toggleSpanStyle(SpanStyle(color = Color.Red))
    //                    },
    //                    onStartAlignClick = {
    //                        state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start))
    //                    },
    //                    onEndAlignClick = {
    //                        state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End))
    //                    },
    //                    onCenterAlignClick = {
    //                        state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
    //                    },
    //                    onExportClick = {
    //                        Log.d("Editor", state.toHtml())
    //                    }
    //                )
    
    //                Editor(
    //                    modifier = Modifier.navigationBarsPadding(),
    //                    state = state,
    //                    onBoldClick = {
    //                        state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
    //                    },
    //                    onItalicClick = {
    //                        state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
    //                    },
    //                    onUnderlineClick = {
    //                        state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
    //                    },
    //                    onTitleClick = {
    //                    },
    //                    onSubtitleClick = {
    //                    },
    //                    onTextColorClick = {
    //                        state.toggleSpanStyle(SpanStyle(color = Color.Red))
    //                    },
    //                    onStartAlignClick = {
    //                    },
    //                    onEndAlignClick = {
    //                    },
    //                    onCenterAlignClick = {
    //                    },
    //                    onExportClick = {
    //                        Log.d("Editor", state.toMarkdown())
    //                    }
    //                )
    
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
                    Image(
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = "Share",
                        modifier = Modifier.padding(end = 13.dp)
                    )
                    Image(painter = painterResource(id = R.drawable.comment),
                        contentDescription = "Comment",
                        modifier = Modifier.padding(end = 13.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.more),
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
    
    
    @Composable
    fun ExtendedSpansText(
        text: AnnotatedString,
        modifier: Modifier = Modifier,
    ) {
        val underlineAnimator = rememberSquigglyUnderlineAnimator()
        val extendedSpans = remember {
            ExtendedSpans(
                RoundedCornerSpanPainter(
                    cornerRadius = 8.sp,
                    padding = RoundedCornerSpanPainter.TextPaddingValues(horizontal = 4.sp),
                    topMargin = 2.sp,
                    bottomMargin = 2.sp,
                    stroke = RoundedCornerSpanPainter.Stroke(
                        color = Color(0xFFBF97FF).copy(alpha = 0.6f)
                    ),
                ),
                SquigglyUnderlineSpanPainter(
                    width = 4.sp,
                    wavelength = 20.sp,
                    amplitude = 2.sp,
                    bottomOffset = 2.sp,
                    animator = underlineAnimator
                )
            )
        }
    
        Text(
            modifier = modifier.drawBehind(extendedSpans),
            text = remember(text) {
                extendedSpans.extend(text)
            },
            onTextLayout = { result ->
                extendedSpans.onTextLayout(result)
            }
        )
    }
    
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun EditorControls(
        modifier: Modifier = Modifier,
        state: RichTextState,
        onBoldClick: () -> Unit,
        onItalicClick: () -> Unit,
        onUnderlineClick: () -> Unit,
        onTitleClick: () -> Unit,
        onSubtitleClick: () -> Unit,
        onTextColorClick: () -> Unit,
        onStartAlignClick: () -> Unit,
        onEndAlignClick: () -> Unit,
        onCenterAlignClick: () -> Unit,
        onExportClick: () -> Unit,
    ) {
        var boldSelected by rememberSaveable { mutableStateOf(false) }
        var italicSelected by rememberSaveable { mutableStateOf(false) }
        var underlineSelected by rememberSaveable { mutableStateOf(false) }
        var titleSelected by rememberSaveable { mutableStateOf(false) }
        var subtitleSelected by rememberSaveable { mutableStateOf(false) }
        var textColorSelected by rememberSaveable { mutableStateOf(false) }
        var linkSelected by rememberSaveable { mutableStateOf(false) }
        var alignmentSelected by rememberSaveable { mutableIntStateOf(0) }
    
    //    var showLinkDialog by remember { mutableStateOf(false) }
    //
    //    AnimatedVisibility(visible = showLinkDialog) {
    //        LinkDialog(
    //            onDismissRequest = {
    //                showLinkDialog = false
    //                linkSelected = false
    //            },
    //            onConfirmation = { linkText, link ->
    //                state.addLink(
    //                    text = linkText,
    //                    url = link
    //                )
    //                showLinkDialog = false
    //                linkSelected = false
    //            }
    //        )
    //    }
    
        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 10.dp)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ControlWrapper(
                selected = boldSelected,
                onChangeClick = { boldSelected = it },
                onClick = onBoldClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatBold,
                    contentDescription = "Bold Control",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            ControlWrapper(
                selected = italicSelected,
                onChangeClick = { italicSelected = it },
                onClick = onItalicClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatItalic,
                    contentDescription = "Italic Control",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            ControlWrapper(
                selected = underlineSelected,
                onChangeClick = { underlineSelected = it },
                onClick = onUnderlineClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatUnderlined,
                    contentDescription = "Underline Control",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            ControlWrapper(
                selected = titleSelected,
                onChangeClick = { titleSelected = it },
                onClick = onTitleClick
            ) {
                Icon(
                    imageVector = Icons.Default.Title,
                    contentDescription = "Title Control",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            ControlWrapper(
                selected = subtitleSelected,
                onChangeClick = { subtitleSelected = it },
                onClick = onSubtitleClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatSize,
                    contentDescription = "Subtitle Control",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            ControlWrapper(
                selected = textColorSelected,
                onChangeClick = { textColorSelected = it },
                onClick = onTextColorClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatColorText,
                    contentDescription = "Text Color Control",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
    //        ControlWrapper(
    //            selected = linkSelected,
    //            onChangeClick = { linkSelected = it },
    //            onClick = { showLinkDialog = true }
    //        ) {
    //            Icon(
    //                imageVector = Icons.Default.AddLink,
    //                contentDescription = "Link Control",
    //                tint = MaterialTheme.colorScheme.onPrimary
    //            )
    //        }
            ControlWrapper(
                selected = alignmentSelected == 0,
                onChangeClick = { alignmentSelected = 0 },
                onClick = onStartAlignClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatAlignLeft,
                    contentDescription = "Start Align Control",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            ControlWrapper(
                selected = alignmentSelected == 1,
                onChangeClick = { alignmentSelected = 1 },
                onClick = onCenterAlignClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatAlignCenter,
                    contentDescription = "Center Align Control",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            ControlWrapper(
                selected = alignmentSelected == 2,
                onChangeClick = { alignmentSelected = 2 },
                onClick = onEndAlignClick
            ) {
                Icon(
                    imageVector = Icons.Default.FormatAlignRight,
                    contentDescription = "End Align Control",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
    //        ControlWrapper(
    //            selected = true,
    //            selectedColor = MaterialTheme.colorScheme.tertiary,
    //            onChangeClick = { },
    //            onClick = onExportClick
    //        ) {
    //            Icon(
    //                imageVector = Icons.Default.Save,
    //                contentDescription = "Export Control",
    //                tint = MaterialTheme.colorScheme.onPrimary
    //            )
    //        }
        }
    }
    
    @Composable
    fun ControlWrapper(
        selected: Boolean,
        selectedColor: Color = MaterialTheme.colorScheme.primary,
        unselectedColor: Color = MaterialTheme.colorScheme.inversePrimary,
        onChangeClick: (Boolean) -> Unit,
        onClick: () -> Unit,
        content: @Composable () -> Unit
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 6.dp))
                .clickable {
                    onClick()
                    onChangeClick(!selected)
                }
                .background(
                    if (selected) selectedColor
                    else unselectedColor
                )
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .padding(all = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }