package com.example.standardblognote.ui.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.standardblognote.model.Recent


@Composable
fun RecentList(recents: List<Recent>) {
    LazyRow(modifier = Modifier.padding(10.dp)) {
        itemsIndexed(recents) { _, item ->
            RecentItem(recent = item)
        }
    }
}