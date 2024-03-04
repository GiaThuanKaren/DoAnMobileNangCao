package com.example.standardblognote.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.standardblognote.ui.Components.UnderLinedTextComponent

@Composable
fun LoginScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                NormalTextComponent(value = stringResource(id = R.string.hello))
                HeadingTextComponent(value = stringResource(id = R.string.welcome))
                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.message)
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource(id = R.drawable.lock)
                )
                Spacer(modifier = Modifier.heightIn(40.dp))
                UnderLinedTextComponent(value = stringResource(id = R.string.forgot_password))
                Spacer(modifier = Modifier.heightIn(40.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.login),

                  )
                Spacer(modifier = Modifier.heightIn(20.dp))
                DividerTextComponent()

                GoogleAndFacebookImages()
                Spacer(modifier = Modifier.heightIn(40.dp))
                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                   // PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
                })
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}