package com.example.standardblognote.data.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.standardblognote.data.rules.Validator
import com.example.standardblognote.model.UserModel
import com.example.standardblognote.navigation.NavigationItem
import com.example.standardblognote.navigation.Navigator
import com.example.standardblognote.network.RetrofitInstance
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("Use UID", Context.MODE_PRIVATE)
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
                Log.d(TAG,"Change email")
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }

            else -> {}
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

    /*private fun login() {

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
                    Navigator.navigate(NavigationItem.Home)
                }
            }
            .addOnFailureListener {
                Log.d(TAG,"Inside_login_failure")
                Log.d(TAG,"${it.localizedMessage}")

                loginInProgress.value = false

            }

    }*/

// login data in serve
    private fun login() {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getUsers()
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    val users = userResponse?.data
                    val user = users?.find { it.username.equals(email, ignoreCase = true) && it.password == password } // Sử dụng equalsIgnoreCase() để so sánh tên đăng nhập

                    if (user != null) {
                        // Đăng nhập thành công
                        loginInProgress.value = false
                        sharedPreferences.edit().putString("uid", user.id).apply()
                        Navigator.navigate(NavigationItem.Home)
                    } else {
                        // Thông báo lỗi
                        loginInProgress.value = false
                        Log.d(TAG, "Email hoặc mật khẩu không đúng")
                    }
                } else {
                    // Thông báo lỗi
                    loginInProgress.value = false
                    Log.d(TAG, "Lỗi khi lấy dữ liệu từ server")
                }
            } catch (e: Exception) {
                // Xử lý lỗi
                loginInProgress.value = false
                Log.e(TAG, "Lỗi không xác định: ${e.message}")
            }
        }
    }




        private val _loginSuccess = MutableLiveData<Boolean>()
        val loginSuccess: LiveData<Boolean> = _loginSuccess

//        fun signInWithGoogle(account: GoogleSignInAccount) {
//            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//            FirebaseAuth.getInstance().signInWithCredential(credential)
//                .addOnCompleteListener { task ->
//
//
//                    if (task.isSuccessful) {
//                        _loginSuccess.value = true
//
//                        Navigator.navigate(NavigationItem.Home)
//                    } else {
//                        Log.w("FirebaseAuth", "signInWithCredential:failure", task.exception)
//                        _loginSuccess.value = false
//                    }
//                }
//        }


    private var isFirstTime = true
    fun signInWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser

                    val email = account.email ?: "" // Sử dụng email hoặc chuỗi rỗng nếu email là null
                    val isFirstLogin = isFirstTime // Kiểm tra xem đây có phải là lần đăng nhập đầu tiên hay không

                    if (user != null) {
                        val uuid = user.uid // UUID của người dùng
                        _loginSuccess.value = true
                        sharedPreferences.edit().putString("uid", uuid).apply()
                        // Thêm dữ liệu vào máy chủ với mật khẩu rỗng
                        val userModel = UserModel(uuid, "Walk-in User", email, "", "", 3)
                        // Gọi phương thức CreateNewUser bên trong một coroutine

                        viewModelScope.launch {
                            try {
                                if (isFirstLogin) {
                                    // Chỉ gọi Retrofit để tạo mới người dùng khi là lần đăng nhập đầu tiên
                                val a =   RetrofitInstance.api.getUserProfile(user.uid)
                                    if(a.isSuccessful){
                                        val userResponse = a.body()
                                        userResponse?.let {
                                            if (it.data == null) {
                                                RetrofitInstance.api.CreateNewUser(userModel)
                                                isFirstTime = false
                                            } else {
                                                isFirstTime = true
                                            }
                                        }

                                    }

                                }
                            } catch (e: Exception) {
                                Log.e("CreateNewUser", "Error: $e")
                                // Xử lý lỗi nếu cần
                            }
                        }

                        Navigator.navigate(NavigationItem.Home)
                    } else {
                        Log.e("FirebaseAuth", "User is null")
                        _loginSuccess.value = false
                    }
                } else {
                    Log.w("FirebaseAuth", "signInWithCredential:failure", task.exception)
                    _loginSuccess.value = false
                }
            }
    }


}