package com.example.standardblognote.ui.Components

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.standardblognote.R

data class Item(
    val id: String?,
    val documentIcon: String?,
    val active: Boolean?,
    val expanded: Boolean?,
    val isSearch: Boolean?,
    val level: Int?,
    val onExpand: () -> Unit?,
    val label: String,
    val onClick: () -> Unit,
    val icon: Int
)


@Composable
fun ItemDocument(
    item: Item
) {
    val (id, documentIcon, active, expanded, isSearch, level, onExpand, label, onClick, icon) = item

    fun handleExpand() {
        item.onExpand?.invoke()
    }

    Column {
        Box(
            modifier = Modifier
                .height(45.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            Row(
                modifier = Modifier
                    .padding(start = item.level?.let { ((it * 12) + 12).dp } ?: 12.dp, end = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (!item.id.isNullOrEmpty()) {
                        Box(modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                handleExpand()
                            },
                            contentAlignment = Alignment.Center,
                        ) {
                            val ChevronIcon =
                                if (item.expanded == true)
                                    Image(
                                        painter = painterResource(id = R.drawable.chevronright),
                                        contentDescription = "ChevronRight",
                                    )
                                else
                                    Image(
                                        painter = painterResource(id = R.drawable.chevrondown),
                                        contentDescription = "ChevronDown",
                                    )
                        }
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    if (!item.documentIcon.isNullOrEmpty()) {
                        Text(text = "icon")
                    } else {
                        Image(painter = painterResource(id = R.drawable.file), contentDescription = "FileIcon")
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        Text(
                            text = "${item.label}",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.W500)),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Row() {
                    if (!item.id.isNullOrBlank()) {
                        Image(painter = painterResource(id = R.drawable.plus),
                            contentDescription = "CreateTitle"
                        )
                    }
                }
            }
        }
        Divider()
    }


}

@Composable
fun ItemSkeleton(level: Int? = null) {
    val paddingLeft = level?.let { (it * 12) + 25 } ?: 12

    Surface(
        modifier = Modifier.padding(vertical = 3.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color.LightGray
    ) {
        Row(
            modifier = Modifier.padding(start = paddingLeft.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Skeleton(modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp))
            Skeleton(modifier = Modifier
                .weight(1f)
                .height(24.dp)
                .padding(end = 8.dp))
        }
    }
}