package com.example.standardblognote.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.standardblognote.R

@Composable
fun Navbar(emailId: String?) {

    var showSheet by remember {
        mutableStateOf(false)
    }

    if (showSheet) {
        BottomSheet(emailId) {
            showSheet = false
        }
    }

    Column(
        modifier = Modifier.clickable { showSheet = true }
    ) {
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
                    text = "GuestUser's Notion",
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BottomSheet(emailId: String?, onDismiss: () -> Unit) {
    val modalBottomSheetState = androidx.compose.material3.rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        BottomSheetItem(emailId)
    }
}

@Composable
fun BottomSheetItem(emailId: String ?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Account",
            fontSize = 17.sp,
            fontFamily = FontFamily(Font(R.font.inter_semibold, FontWeight.W500))
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .background(Color.White)
                        .padding(start = 10.dp)
                ) {
                    Box(modifier = Modifier.size(40.dp)) {
                        Image(painter = painterResource(id = R.drawable.avatar), contentDescription = "AvatarGuest")
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "${emailId}'s Notion",
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.W500))
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
                        .align(Alignment.BottomStart)
                        .padding(top = 79.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(Color.White)
                    .padding(start = 10.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.logout), contentDescription = "Logout")
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Logout",
                    fontSize = 16.sp,
                    color = Color(248, 68, 100),
                    fontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.W500))
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}