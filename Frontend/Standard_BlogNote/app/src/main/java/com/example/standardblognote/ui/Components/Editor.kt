package com.example.standardblognote.ui.Components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.standardblognote.R

data class ItemChild(
    val data: List<MenuItem>
)

data class MenuItem(
    val icon: Int,
    val children: ItemChild? = null
)


@Composable
fun Editor(modifier: Modifier) {

    val menuItems = remember {
        listOf(
            MenuItem(
                icon = R.drawable.ai,
                children = ItemChild(
                    data = listOf(
                        MenuItem(
                            icon = R.drawable.plus,
                        ),
                    )
                )
            ),
            MenuItem(
                icon = R.drawable.bold,
            ),
            MenuItem(
                icon = R.drawable.italic,
            ),
            MenuItem(
                icon = R.drawable.underline,
            ),
            MenuItem(
                icon = R.drawable.strike,
            ),
            MenuItem(
                icon = R.drawable.code,
            ),
            MenuItem(
                icon = R.drawable.equation,
            ),
        )
    }
    var history by rememberSaveable {
        mutableStateOf(listOf(ItemChild(data = menuItems)))
    }
    val current = history.last()
    val HandleBack: () -> Unit = {
        history = history.dropLast(1)
    }
    val RenderItems: @Composable () -> Unit = {
        LazyRow(
            modifier = modifier
        ) {
            itemsIndexed(current.data) { _, item ->
                val isParent = item?.children != null
                ItemEditor(
                    onHistory = {
                        if (isParent) {
                            history = history + listOf(item.children!!)

                        } else {

                        }
                                },
                    data = item
                )
            }
        }
    }
    RenderItems()
}
