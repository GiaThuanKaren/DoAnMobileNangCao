package com.example.standardblognote.data.login
import android.app.Activity
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.standardblognote.data.rules.Validator
import com.example.standardblognote.model.UserModel
import com.example.standardblognote.navigation.PostOfficeAppRouter
import com.example.standardblognote.navigation.Screen
import com.example.standardblognote.network.RetrofitInstance
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)


    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
        validateLoginUIDataWithRules()
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )


        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status

    }

    private fun login() {

        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG,"Inside_login_success")
                Log.d(TAG,"${it.isSuccessful}")

                if(it.isSuccessful){
                    loginInProgress.value = false
                    PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                }
            }
            .addOnFailureListener {
                Log.d(TAG,"Inside_login_failure")
                Log.d(TAG,"${it.localizedMessage}")

                loginInProgress.value = false

            }

    }






        private val _loginSuccess = MutableLiveData<Boolean>()
        val loginSuccess: LiveData<Boolean> = _loginSuccess

        fun signInWithGoogle(account: GoogleSignInAccount) {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _loginSuccess.value = true
                        PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                    } else {
                        Log.w("FirebaseAuth", "signInWithCredential:failure", task.exception)
                        _loginSuccess.value = false
                    }
                }
        }


//    private fun login() {
//        loginInProgress.value = true
//        val email = loginUIState.value.email
//        val password = loginUIState.value.password
//
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val currentUser = FirebaseAuth.getInstance().currentUser
//                    if (currentUser != null) {
//                        val username = currentUser.displayName ?: email // Sử dụng email nếu displayName không tồn tại
//
//                            val userModel = UserModel(username = username, authType = 2) // authType = 2 cho lần đăng ký đầu tiên
//                            saveUserToServer(userModel)
//
//                            // Đã đăng nhập trước đó, không cần lưu thông tin người dùng lên server
////                            loginInProgress.value = false
////                            PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
//
//                    } else {
//                        Log.e(TAG, "Current user is null.")
//                        loginInProgress.value = false
//                        // Xử lý lỗi
//                    }
//                } else {
//                    Log.e(TAG, "Sign-in failed: ${task.exception?.message}")
//                    loginInProgress.value = false
//                    // Xử lý lỗi
//                }
//            }
//            .addOnFailureListener { e ->
//                Log.e(TAG, "Sign-in failed: ${e.message}")
//                loginInProgress.value = false
//                // Xử lý lỗi
//            }
//    }
//
//    private fun saveUserToServer(userModel: UserModel) {
//        GlobalScope.launch {
//            try {
//                val response = RetrofitInstance.api.SaveUser(userModel)
//                if (response.isSuccessful) {
//                    // Lưu thành công
//                    loginInProgress.value = false
//                    PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
//                } else {
//                    // Xử lý lỗi khi gửi thông tin lên server
//                    Log.e(TAG, "Failed to save user information: ${response.message()}")
//                    loginInProgress.value = false
//                    // Xử lý lỗi
//                }
//            } catch (e: Exception) {
//                // Xử lý lỗi khi gửi thông tin lên server
//                Log.e(TAG, "Error: ${e.message}")
//                loginInProgress.value = false
//                // Xử lý lỗi
//            }
//        }
//    }





}