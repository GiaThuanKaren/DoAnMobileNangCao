package com.example.standardblognote.ui.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.glance.text.Text

class RegisterPremiumActivity: AppCompatActivity() , View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ){
                UiRegisterPremiumActivity()
            }

        }
        
    }
    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
    }

}

@Composable
fun UiRegisterPremiumActivity(){
    Text(text = "Xin Chao")
}