package com.example.standardblognote.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.standardblognote.R

@Composable
fun Navbar() {
    Column {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp)) {
                    Image(painter = painterResource(id = R.drawable.avatar), contentDescription = "AvatarGuest")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "standardLord's Notion",
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.W500))
                )
                Spacer(modifier = Modifier.width(5.dp))
                Image(
                    painter = painterResource(id = R.drawable.chevronupdown),
                    contentDescription = "ExpandedableName",
                    modifier = Modifier.size(24.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.bell),
                    contentDescription = "Notifications",
                    modifier = Modifier.padding(end = 13.dp)
                )
                Image(painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search"
                )
            }
        }
        Divider()
    }
}