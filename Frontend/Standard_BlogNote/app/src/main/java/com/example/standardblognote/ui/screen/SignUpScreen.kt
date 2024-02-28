package com.example.standardblognote.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.standardblognote.R
import com.example.standardblognote.ui.Components.ButtonComponent
import com.example.standardblognote.ui.Components.ClickableLoginTextComponent
import com.example.standardblognote.ui.Components.DividerTextComponent
import com.example.standardblognote.ui.Components.GoogleAndFacebookImages
import com.example.standardblognote.ui.Components.HeadingTextComponent
import com.example.standardblognote.ui.Components.MyTextFieldComponent
import com.example.standardblognote.ui.Components.NormalTextComponent
import com.example.standardblognote.ui.Components.PasswordTextFieldComponent

@Composable
fun SignUpScreen() {
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ){
        Column(modifier = Modifier.fillMaxSize()){
            Spacer(modifier = Modifier.height(60.dp))
            NormalTextComponent(value = stringResource(id = R.string.hello))
            HeadingTextComponent(value = stringResource(id = R.string.create_account))
            Spacer(modifier = Modifier.height(30.dp))
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                painterResource(id = R.drawable.message)
            )
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password),
                painterResource(id = R.drawable.ic_lock)
            )

            Spacer(modifier = Modifier.height(40.dp))
            ButtonComponent(
                value = stringResource(id = R.string.register)
            )
            Spacer(modifier = Modifier.height(50.dp))
            DividerTextComponent()

            ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {

            })

        }



    }
}


@Preview
@Composable
fun DefaultPreviewOfSignUpScreen(){

    SignUpScreen()
}