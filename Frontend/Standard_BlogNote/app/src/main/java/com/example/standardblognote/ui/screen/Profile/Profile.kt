package com.example.standardblognote.ui.screen.Profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController

import com.example.standardblognote.R


@Composable
fun Profile(openProfileDetail :()->Unit ,navController: NavHostController){
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(android.graphics.Color.parseColor("#ececec"))),
        horizontalAlignment = Alignment.CenterHorizontally
    )    {
      ConstraintLayout() {
          val(topImg,profile)=createRefs()
          Image(painterResource(id = R.drawable.top_background),null,
              Modifier
                  .fillMaxWidth()
                  .constrainAs(topImg) {
                      top.linkTo(parent.top)
                      start.linkTo(parent.start)
                  })
          Image(painterResource(id = R.drawable.profile),null,
              Modifier

                  .constrainAs(profile){
                      top.linkTo(topImg.bottom)
                      bottom.linkTo(topImg.bottom)
                      start.linkTo(parent.start)
                      end.linkTo(parent.end)
                  })
     }
        Text(text = "Nguyen Van A",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp),
            color = Color(android.graphics.Color.parseColor("#747679"))
        )
        Text(text = "nguyenvana@gmail.com",
            fontSize = 22.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(top = 16.dp),
            color = Color(android.graphics.Color.parseColor("#747679"))
        )
        Button(onClick = {  openProfileDetail()
                  //       navController.navigate("ProfileDetail")
        },
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp), colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#ffffff"))
                ))
        {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_1), contentDescription = "",
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { })

            }
            Column( modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start)
            {
                Text(text = "Chinh sua thong tin ",
                    color =Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold )
            }
        }
        Button(onClick = { /*TODO*/ },
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp), colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#ffffff"))
            ))
        {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_2), contentDescription = "",
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { })

            }
            Column( modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start)
            {
                Text(text = "Chinh sua thong tin ",
                    color =Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold )
            }
        }
        Button(onClick = { /*TODO*/ },
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp), colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#ffffff"))
            ))
        {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_3), contentDescription = "",
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { })

            }
            Column( modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start)
            {
                Text(text = "Chinh sua thong tin ",
                    color =Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold )
            }
        }
        Button(onClick = { /*TODO*/ },
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp), colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#ffffff"))
            ))
        {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_4), contentDescription = "",
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .clickable { })

            }
            Column( modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start)
            {
                Text(text = "Chinh sua thong tin ",
                    color =Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold )
            }
        }
        Button(onClick = { /*TODO*/ },
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp), colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#373ebf"))
        ), shape = RoundedCornerShape(50)
        )
        {
            Text(text = "Quay lai trang chu",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun GreetingText() {
    Text(text = "nguyen van a ")
}

