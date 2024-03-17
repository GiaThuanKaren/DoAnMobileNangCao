package com.example.standardblognote.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.standardblognote.R

@Composable
fun ItemEditor(
    onHistory: () -> Unit,
    selected: Boolean,
    selectedColor: Color = Color.Red,
    unSelectedColor: Color = Color.Blue,
    onChangeClick: (Boolean) -> Unit?
) {

    Box(
        modifier = Modifier.size(40.dp)
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable {
                onHistory()
                onChangeClick(!selected)
            }
            .background(
                if (selected) selectedColor
                else unSelectedColor
            )
            .border(
                width = 1.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
//        Image(
//            painter = painterResource(id = data.icon),
//            contentDescription = "editor_${data.icon}",
//            modifier = Modifier
//                .size(34.dp)
//                .padding(5.dp)
//                .clickable { onHistory() },
//        )
    }
}