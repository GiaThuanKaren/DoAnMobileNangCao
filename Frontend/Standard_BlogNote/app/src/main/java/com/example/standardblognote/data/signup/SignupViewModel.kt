package com.example.standardblognote.data.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.standardblognote.data.RegistrationUIState
import com.example.standardblognote.data.rules.Validator
import com.example.standardblognote.model.UserModel
import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Navigator
import com.example.standardblognote.network.RetrofitInstance
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel() : ViewModel() {

    private val TAG = SignupViewModel::class.simpleName


    var registrationUIState = mutableStateOf(RegistrationUIState())

    var allValidationsPassed = mutableStateOf(false)

    var signUpInProgress = mutableStateOf(false)

//    constructor() : this(Navigator())

    fun onEvent(event: SignupUIEvent) {
        when (event) {
//            is SignupUIEvent.FirstNameChanged -> {
//                registrationUIState.value = registrationUIState.value.copy(
//                    firstName = event.firstName
//                )
//                printState()
//            }
//
//            is SignupUIEvent.LastNameChanged -> {
//                registrationUIState.value = registrationUIState.value.copy(
//                    lastName = event.lastName
//                )
//                printState()
//            }

            is SignupUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                printState()

            }


            is SignupUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
                printState()

            }

            is SignupUIEvent.RegisterButtonClicked -> {
                signUp()
            }

            is SignupUIEvent.PrivacyPolicyCheckBoxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    privacyPolicyAccepted = event.status
                )
            }
        }
        validateDataWithRules()
    }


    private fun signUp() {
        Log.d(TAG, "Inside_signUp")
        printState()
        createUserInFirebase(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password
        )
    }

    private fun validateDataWithRules() {
//        val fNameResult = Validator.validateFirstName(
//            fName = registrationUIState.value.firstName
//        )
//
//        val lNameResult = Validator.validateLastName(
//            lName = registrationUIState.value.lastName
//        )

        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )


        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )

        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = registrationUIState.value.privacyPolicyAccepted
        )


        Log.d(TAG, "Inside_validateDataWithRules")
//        Log.d(TAG, "fNameResult= $fNameResult")
//        Log.d(TAG, "lNameResult= $lNameResult")
        Log.d(TAG, "emailResult= $emailResult")
        Log.d(TAG, "passwordResult= $passwordResult")
        Log.d(TAG, "privacyPolicyResult= $privacyPolicyResult")

        registrationUIState.value = registrationUIState.value.copy(
//            firstNameError = fNameResult.status,
//            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            privacyPolicyError = privacyPolicyResult.status
        )


//        allValidationsPassed.value = fNameResult.status && lNameResult.status &&
//                emailResult.status && passwordResult.status && privacyPolicyResult.status
        allValidationsPassed.value =
            emailResult.status && passwordResult.status && privacyPolicyResult.status

    }


    private fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }


//    private fun createUserInFirebase(email: String, password: String) {
//
//        signUpInProgress.value = true
//        FirebaseAuth
//            .getInstance()
//            .createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener {
//                Log.d(TAG, "Inside_OnCompleteListener")
//                Log.d(TAG, " isSuccessful = ${it.isSuccessful}")
//                signUpInProgress.value = false
//
//                if (it.isSuccessful) {
//
//
//                    Navigator.navigate(NavigationItem.Home)
//                }
//            }
//            .addOnFailureListener {
//                Log.d(TAG, "Inside_OnFailureListener")
//                Log.d(TAG, "Exception= ${it.message}")
//                Log.d(TAG, "Exception= ${it.localizedMessage}")
//            }
//    }


    private fun createUserInFirebase(email: String, password: String) {
        signUpInProgress.value = true

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                signUpInProgress.value = false
                if (task.isSuccessful) {
                    val userModel = UserModel(email, password, password, 2)
                    CoroutineScope(Dispatchers.IO).launch {
                        RetrofitInstance.api.CreateNewUser(userModel)
                    }
                    Navigator.navigate(NavigationItem.Home)
                } else {
                    // Xử lý khi tạo tài khoản thất bại
                    Log.d(TAG, "Creation failed: ${task.exception?.message}")
                }
            }
            .addOnFailureListener { exception ->
                // Xử lý khi có lỗi xảy ra trong quá trình tạo tài khoản
                Log.d(TAG, "Creation failed: ${exception.message}")
            }
    }
}



