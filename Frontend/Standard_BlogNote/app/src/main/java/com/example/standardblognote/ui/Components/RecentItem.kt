package com.example.standardblognote.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.standardblognote.R
import com.example.standardblognote.model.Recent

@Composable
fun RecentItem(recent: Recent) {
    Column(
        modifier = Modifier
            .padding(top = 15.dp)
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min)
            .padding(end = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        recent?.coverImage?.let { painterResource(id = it) }?.let {
            Image(
                painter = it,
                contentDescription = "ImageCover",
                modifier = Modifier
                    .width(170.dp)
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = "${recent.title}",
            fontSize = 17.sp,
            modifier = Modifier
                .width(170.dp)
                .padding(top = 15.dp, end = 5.dp),
            fontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.W500)),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun DarkStoryItemPreview() {
//    RecentItem(recent = Recent(coverImage = "", title = ""))
}