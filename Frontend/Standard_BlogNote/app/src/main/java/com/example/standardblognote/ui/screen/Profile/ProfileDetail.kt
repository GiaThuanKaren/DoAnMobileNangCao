package com.example.standardblognote.ui.screen.Profile


import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.standardblognote.R
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.ui.Components.Navbar

@Composable
fun ProfileDetail( navController: NavHostController, homeViewModel: HomeViewModel = viewModel()){
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(android.graphics.Color.parseColor("#ececec"))),
        horizontalAlignment = Alignment.CenterHorizontally
    )    {
        Navbar()
        ConstraintLayout() {
            val(topImg,profile)=createRefs()
            Image(
                painterResource(id = R.drawable.top_background),null,
                Modifier
                    .fillMaxWidth()
                    .constrainAs(topImg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })
            Image(
                painterResource(id = R.drawable.profile),null,
                Modifier

                    .constrainAs(profile){
                        top.linkTo(topImg.bottom)
                        bottom.linkTo(topImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }


        ProfileDetailTextFile()
    Column( modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center) {
        Button(onClick = { /*TODO*/ },
            Modifier
                .fillMaxWidth()

                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(60.dp)
                , colors = ButtonDefaults.buttonColors(
                Color(android.graphics.Color.parseColor("#3399FF"))
            ), shape = RoundedCornerShape(50)
        )
        {
            Text(text = "Luu lại",
                fontSize = 20.sp,
                color = androidx.compose.ui.graphics.Color.Black,
                fontWeight = FontWeight.Bold)
        }
        Button(onClick = {  navController.popBackStack() },
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(60.dp)
                , colors = ButtonDefaults.buttonColors(
                Color(android.graphics.Color.parseColor("#FF3333"))
            ), shape = RoundedCornerShape(50)
        )
        {
            Text(text = "Quay lại trang chủ",
                fontSize = 20.sp,
                color = androidx.compose.ui.graphics.Color.Black,
                fontWeight = FontWeight.Bold)

        }
    }


    }
}

@Composable
fun ProfileDetailTextFile( ){
    var fistname by remember {
        mutableStateOf("String")
    }
    TextField(value = fistname,modifier = Modifier.padding(top = 20.dp, bottom = 10.dp, start = 10.dp), onValueChange = {
        newValue->fistname=newValue
    }, textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = {Icon(Icons.Default.Person, contentDescription = "name")},
        trailingIcon = {
            IconButton(onClick = { fistname=""}) {
                Icon(Icons.Default.Close, contentDescription =null)
            }
        }

    )
    var email by remember {
        mutableStateOf("12345@gmail.com")
    }
    TextField(value = email, modifier = Modifier.padding( bottom = 10.dp, start = 10.dp), onValueChange = {
            newValue->email=newValue
    }, textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = {Icon(Icons.Default.Email, contentDescription = "name")},
        trailingIcon = {
            IconButton(onClick = { email=""}) {
                Icon(Icons.Default.Close, contentDescription =null)
            }
        }

    )

    var Phone by remember {
        mutableStateOf("0867630432")
    }
    TextField(value = Phone,modifier = Modifier
        .padding( bottom = 10.dp, start = 10.dp)

        ,shape = RoundedCornerShape(50),
        onValueChange = {
            newValue->Phone=newValue
    }, textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = {Icon(Icons.Default.Phone, contentDescription = "name")},
        trailingIcon = {
            IconButton(onClick = { Phone=""}) {
                Icon(Icons.Default.Close, contentDescription =null)
            }
        }

    )

    var Adreest by remember {
        mutableStateOf("0867630432")
    }
    TextField(value = Adreest,modifier = Modifier.padding( bottom = 10.dp, start = 10.dp), onValueChange = {
            newValue->Adreest=newValue
    }, textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.W500),
        leadingIcon = {Icon(Icons.Default.LocationOn, contentDescription = "name")},
        trailingIcon = {
            IconButton(onClick = { Adreest=""}) {
                Icon(Icons.Default.Close, contentDescription =null)
            }
        }

    )
}

