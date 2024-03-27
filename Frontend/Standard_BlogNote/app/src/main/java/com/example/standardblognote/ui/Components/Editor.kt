package com.example.standardblognote.ui.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.standardblognote.R
import com.mohamedrejeb.richeditor.model.RichTextState

//data class ItemChild(
//    val data: List<MenuItem>
//)

//data class MenuItem(
//    val icon: Int,
//    val feature: () -> Unit = {},
//    val children: ItemChild? = null,
//    val selected: Boolean? = false,
//    val onChangeClick: ((Boolean) -> Unit)? = null
//)


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Editor(
    modifier: Modifier,
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

    var showLinkDialog by remember { mutableStateOf(false) }



//    val menuItems = remember {
//        listOf(
//            MenuItem(
//                icon = R.drawable.ai,
//                children = ItemChild(
//                    data = listOf(
//                        MenuItem(
//                            icon = R.drawable.plus,
//                        ),
//                    )
//                )
//            ),
//            MenuItem(
//                icon = R.drawable.bold,
//                feature = onBoldClick,
//                selected = boldSelected,
//                onChangeClick = { boldSelected = it }
//            ),
//            MenuItem(
//                icon = R.drawable.italic,
//                feature = onItalicClick,
//                selected = italicSelected,
//                onChangeClick = { italicSelected = it }
//            ),
//            MenuItem(
//                icon = R.drawable.underline,
//                feature = onUnderlineClick,
//                selected = underlineSelected,
//                onChangeClick = { underlineSelected = it }
//            ),
//            MenuItem(
//                icon = R.drawable.strike,
//                feature = onExportClick
//            ),
//            MenuItem(
//                icon = R.drawable.link,
//                feature = onExportClick
//            ),
//            MenuItem(
//                icon = R.drawable.code,
//            ),
//            MenuItem(
//                icon = R.drawable.equation,
//            ),
//        )
//    }
//    var history by rememberSaveable {
//        mutableStateOf(listOf(ItemChild(data = menuItems)))
//    }
//    val current = history.last()
//    val HandleBack: () -> Unit = {
//        history = history.dropLast(1)
//    }
//    val RenderItems: @Composable () -> Unit = {
//        LazyRow(
//            modifier = modifier
//        ) {
//            itemsIndexed(current.data) { _, item ->
//                val isParent = item?.children != null
//                ItemEditor(
//                    onHistory = {
//                        if (isParent) {
//                            history = history + listOf(item.children!!)
//
//                        } else {
//                            item?.feature?.invoke()
//                        }
//                                },
//                    data = item,
//                    selected = item?.selected!!,
//                    onChangeClick = item?.onChangeClick ?: { _ -> }
//
//                )
//            }
//        }
//    }
//    RenderItems()

    FlowRow(
        modifier = modifier
    ) {
        ControlWrapper(
            selected = boldSelected,
            onChangeClick = { boldSelected = it },
            onClick = onBoldClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.bold),
                contentDescription = "editor",
            )
        }
        ControlWrapper(
            selected = italicSelected,
            onChangeClick = { italicSelected = it },
            onClick = onItalicClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.italic),
                contentDescription = "editor",
            )
        }
        ControlWrapper(
            selected = underlineSelected,
            onChangeClick = { underlineSelected = it },
            onClick = onUnderlineClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.underline),
                contentDescription = "editor",
            )
        }
    }
}

@Composable
fun ControlWrapper(
    selected: Boolean,
    selectedColor: Color = Color.LightGray,
    unselectedColor: Color = Color.Transparent,
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
                color = Color.Transparent,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
