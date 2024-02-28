package com.example.standardblognote.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.standardblognote.R

@Composable
fun ItemEditor(onHistory: () -> Unit, data: MenuItem) {

    Image(
        painter = painterResource(id = data.icon),
        contentDescription = "editor_${data.icon}",
        modifier = Modifier
            .size(34.dp)
            .padding(5.dp)
            .clickable { onHistory() },
    )
}