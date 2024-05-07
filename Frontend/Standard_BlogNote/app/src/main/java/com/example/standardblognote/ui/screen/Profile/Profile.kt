package com.example.standardblognote.ui.screen.Profile


import ProfileViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.standardblognote.R
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.navigation.NavigationItem


@Composable
fun Profile(openProfileDetail :()->Unit ,navController: NavHostController, homeViewModel: HomeViewModel) {

    val profileViewModel: ProfileViewModel = viewModel()

    //    val uid by homeViewModel.uid.observeAsState()
    val emailId by homeViewModel.emailId.observeAsState()
    //    val NotificationService = NotificationService(context = context)

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    // Lấy UID từ SharedPreferences
    val uid = homeViewModel.getUidFromSharedPreferences() ?: ""
    if (uid == "") {
        LaunchedEffect(Unit) {
            homeViewModel.fetchUidLogin()
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.getUserData()
    }
    // Fetch user profile
    profileViewModel.fetchUser(uid)

    val userLiveData = profileViewModel.userLiveData

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(android.graphics.Color.parseColor("#ececec"))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout() {
            val (topImg, profile) = createRefs()
            Image(painterResource(id = R.drawable.top_background), null,
                Modifier
                    .fillMaxWidth()
                    .constrainAs(topImg) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })
            Image(painterResource(id = R.drawable.profile), null,
                Modifier

                    .constrainAs(profile) {
                        top.linkTo(topImg.bottom)
                        bottom.linkTo(topImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }
        Text(
            text = userLiveData.value.username,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp),
            color = Color(android.graphics.Color.parseColor("#747679"))
        )
        Text(
            text = userLiveData.value.email,
            fontSize = 22.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(top = 16.dp),
            color = Color(android.graphics.Color.parseColor("#747679"))
        )
        Button(
            onClick = {
                openProfileDetail()
            },
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp), colors = ButtonDefaults.buttonColors(
                Color(android.graphics.Color.parseColor("#ffffff"))
            )
        )
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
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            )
            {
                Text(
                    text = "Xem thông Tin Cá Nhân ",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Button(
            onClick = {
                navController.navigate("ChangePasword")
            },
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp), colors = ButtonDefaults.buttonColors(
                Color(android.graphics.Color.parseColor("#ffffff"))
            )
        )
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
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            )
            {
                Text(
                    text = "Đổi Mật Khẩu và Tên ",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
//        Button(
//            onClick = { /*TODO*/ },
//            Modifier
//                .fillMaxWidth()
//                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
//                .height(55.dp), colors = ButtonDefaults.buttonColors(
//                Color(android.graphics.Color.parseColor("#ffffff"))
//            )
//        )
//        {
//            Column(
//                modifier = Modifier.fillMaxHeight(),
//                verticalArrangement = Arrangement.Center
//            ) {
//                Image(painter = painterResource(id = R.drawable.ic_3), contentDescription = "",
//                    modifier = Modifier
//                        .padding(end = 5.dp)
//                        .clickable { })
//
//            }
//            Column(
//                modifier = Modifier
//                    .padding(start = 16.dp)
//                    .weight(1f),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.Start
//            )
//            {
//                Text(
//                    text = "Đổi Tên Tài Khoản ",
//                    color = Color.Black,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
        Button(
            onClick = { navController.navigate(NavigationItem.Login.route) },
            Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                .height(55.dp), colors = ButtonDefaults.buttonColors(
                Color(android.graphics.Color.parseColor("#ffffff"))
            )
        )
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

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                )
                {
                    Text(
                        text = "Đăng xuất ",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Button(
                onClick = { navController.navigate(NavigationItem.Home.route) },
                Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 10.dp)
                    .height(55.dp), colors = ButtonDefaults.buttonColors(
                    Color(android.graphics.Color.parseColor("#373ebf"))
                ), shape = RoundedCornerShape(50)
            )
            {
                Text(
                    text = "Quay lai trang chu",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }



