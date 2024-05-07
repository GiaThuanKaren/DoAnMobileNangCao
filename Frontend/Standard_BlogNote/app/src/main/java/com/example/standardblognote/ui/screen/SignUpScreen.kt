package com.example.standardblognote.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.standardblognote.R
import com.example.standardblognote.data.home.HomeViewModel
import com.example.standardblognote.data.signup.SignupUIEvent
import com.example.standardblognote.data.signup.SignupViewModel
import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Screen
import com.example.standardblognote.navigation.SystemBackButtonHandler
import com.example.standardblognote.ui.Components.ButtonComponent
import com.example.standardblognote.ui.Components.CheckboxComponent
import com.example.standardblognote.ui.Components.ClickableLoginTextComponent
import com.example.standardblognote.ui.Components.DividerTextComponent
import com.example.standardblognote.ui.Components.HeadingTextComponent
import com.example.standardblognote.ui.Components.MyTextFieldComponent
import com.example.standardblognote.ui.Components.NormalTextComponent
import com.example.standardblognote.ui.Components.PasswordTextFieldComponent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

@Composable
//fun SignUpScreen(navController: NavHostController, signupViewModel: SignupViewModel) {
fun SignUpScreen(navController: NavHostController, signupViewModel: SignupViewModel = viewModel()) {
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
                painterResource = painterResource(id = R.drawable.message),
                onTextChanged = {
                    signupViewModel.onEvent(SignupUIEvent.EmailChanged(it))
                },
                errorStatus = signupViewModel.registrationUIState.value.emailError
            )

            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.ic_lock),
                onTextSelected = {
                    signupViewModel.onEvent(SignupUIEvent.PasswordChanged(it))
                },
                errorStatus = signupViewModel.registrationUIState.value.passwordError
            )
            CheckboxComponent(value = stringResource(id = R.string.terms_and_conditions),
                onTextSelected = {
//                    PostOfficeAppRouter.navigateTo(Screens.TermsAndConditionsScreen)
//                    navigator.navigate(NavigationItem.TermsAndConditions)
                                 navController.navigate("termsandconditions")

                },
                onCheckedChange = {
                    signupViewModel.onEvent(SignupUIEvent.PrivacyPolicyCheckBoxClicked(it))
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            ButtonComponent(
                value = stringResource(id = R.string.register),
                onButtonClicked = {
                    signupViewModel.onEvent(SignupUIEvent.RegisterButtonClicked)
                },
                isEnabled = signupViewModel.allValidationsPassed.value
            )

            Spacer(modifier = Modifier.height(30.dp))
            DividerTextComponent()

            ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                navController.navigate(NavigationItem.Login.route)
            })

        }
        SystemBackButtonHandler {
            navController.navigate(NavigationItem.Login.route)
        }
        if(signupViewModel.signUpInProgress.value) {
            CircularProgressIndicator(
                progress = 0.89f,
            )
        }

    }

}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen(){

//    SignUpScreen()
}