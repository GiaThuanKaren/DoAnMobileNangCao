package com.example.standardblognote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.standardblognote.app.PostOfficeApp
import com.example.standardblognote.model.Recent
//import com.example.standardblognote.navigation.navhost.AppNavHost
import com.example.standardblognote.ui.Components.*
import com.example.standardblognote.ui.screen.DocumentNote
import com.example.standardblognote.ui.theme.StandardBlogNoteTheme

var recents: List<Recent> = emptyList()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            StandardBlogNoteTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    val navController = rememberNavController()
//                    AppNavHost(navController, modifier = Modifier)
//
//                }
//            }
            //      }
            PostOfficeApp()
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun DefaultPreview() {
//    StandardBlogNoteTheme {
//        Column {
//            Row(
//                modifier = Modifier
//                    .padding(10.dp)
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Box(modifier = Modifier.size(40.dp)) {
//                        Image(painter = painterResource(id = R.drawable.avatar), contentDescription = "AvatarGuest")
//                    }
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Text(
//                        text = "standardLord's Notion",
//                        fontSize = 17.sp,
//                        fontFamily = FontFamily(Font(R.font.inter_medium, FontWeight.W500))
//                    )
//                    Spacer(modifier = Modifier.width(5.dp))
//                    Image(
//                        painter = painterResource(id = R.drawable.chevronupdown),
//                        contentDescription = "ExpandedableName",
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Image(
//                        painter = painterResource(id = R.drawable.bell),
//                        contentDescription = "Notifications",
//                        modifier = Modifier.padding(end = 13.dp)
//                    )
//                    Image(painter = painterResource(id = R.drawable.search),
//                        contentDescription = "Search"
//                    )
//                }
//            }
//        }
//    }
//}