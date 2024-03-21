package com.example.standardblognote.ui.screen

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.glance.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.standardblognote.R
//import com.example.standardblognote.data.NavigationItem
import com.example.standardblognote.data.login.LoginUIEvent
import com.example.standardblognote.data.login.LoginViewModel
import com.example.standardblognote.navigation.NavigationItem
//import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Navigator
import com.example.standardblognote.navigation.Screen
import com.example.standardblognote.ui.Components.ButtonComponent
import com.example.standardblognote.ui.Components.ClickableLoginTextComponent
import com.example.standardblognote.ui.Components.DividerTextComponent
import com.example.standardblognote.ui.Components.GoogleLoginButton
import com.example.standardblognote.ui.Components.HeadingTextComponent
//import com.example.standardblognote.ui.Components.LoginWithGoogle
import com.example.standardblognote.ui.Components.MyTextFieldComponent
import com.example.standardblognote.ui.Components.NormalTextComponent
import com.example.standardblognote.ui.Components.PasswordTextFieldComponent
@Composable

// fun LoginScreen(context : Context,loginViewModel: LoginViewModel = viewModel()) {

//fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
fun LoginScreen(context:Context, navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
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
                    painterResource(id = R.drawable.message),
                    onTextChanged = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )

                Spacer(modifier = Modifier.heightIn(40.dp))
                // UnderLinedTextComponent(value = stringResource(id = R.string.forgot_password))
                Spacer(modifier = Modifier.heightIn(40.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.heightIn(20.dp))
                DividerTextComponent()
                Spacer(modifier = Modifier.heightIn(20.dp))

                GoogleLoginButton(context = context,loginViewModel)
                Spacer(modifier = Modifier.heightIn(40.dp))
                ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {

//                    PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
                    navController.navigate(NavigationItem.Signup.route)

                })
            }
        }
            if (loginViewModel.loginInProgress.value) {
                CircularProgressIndicator()
            }
        }
    }

