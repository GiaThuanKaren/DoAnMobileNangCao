package com.example.standardblognote.data.home

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.standardblognote.data.NavigationItem
import com.example.standardblognote.navigation.PostOfficeAppRouter
import com.example.standardblognote.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

//class HomeViewModel : ViewModel() {
class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("Use UID", Context.MODE_PRIVATE)
    private val _uid = MutableLiveData<String?>()
    val uid: LiveData<String?> = _uid
    fun fetchCurrentUserUid() {
        val user = FirebaseAuth.getInstance().currentUser
        _uid.value = user?.uid
        // Lưu UID vào SharedPreferences
        sharedPreferences.edit().putString("uid", user?.uid).apply()
    }

    // Hàm để lấy UID từ SharedPreferences
//    fun getUidFromSharedPreferences(): String? {
//        return sharedPreferences.getString("uid", null)
//    }
    fun clearUid() {
        sharedPreferences.edit().remove("uid").apply()
    }


            private val TAG = HomeViewModel::class.simpleName

    val navigationItemsList = listOf<NavigationItem>(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            description = "Home Screen",
            itemId = "homeScreen"
        ),
        NavigationItem(
            title = "Settings",
            icon = Icons.Default.Settings,
            description = "Settings Screen",
            itemId = "settingsScreen"
        ),
        NavigationItem(
            title = "Favorite",
            icon = Icons.Default.Favorite,
            description = "Favorite Screen",
            itemId = "favoriteScreen"
        )
    )

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun logout() {

        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Inside sign outsuccess")
                PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                clearUid()
            } else {
                Log.d(TAG, "Inside sign out is not complete")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)

    }

    fun checkForActiveSession() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d(TAG, "Valid session")
            isUserLoggedIn.value = true
        } else {
            Log.d(TAG, "User is not logged in")
            isUserLoggedIn.value = false
        }
    }


    val emailId: MutableLiveData<String> = MutableLiveData()

    fun getUserData() {
        FirebaseAuth.getInstance().currentUser?.also {
            it.email?.also { email ->
                emailId.value = email
            }
        }

    }

// lấy use uid
//    private val _uid = MutableLiveData<String?>()
//    val uid: LiveData<String?> = _uid
//
//    fun fetchCurrentUserUid() {
//        val user = FirebaseAuth.getInstance().currentUser
//        _uid.value = user?.uid
//    }
}