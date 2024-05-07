package com.example.standardblognote.ui.screen

import android.app.Activity
import android.content.Context
import android.os.StrictMode
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.standardblognote.model.CreateOrder
import org.json.JSONObject
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener


@Composable
fun RegisterPremiumScreen() {
    val context = LocalContext.current
    val intent = (context as Activity).intent
    // Thanh toan ZaloPay
    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)

    ZaloPaySDK.init(2554, Environment.SANDBOX)

    LaunchedEffect(Unit) {
        ZaloPaySDK.getInstance().onResult(intent)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { RequestZalo(context) },
        ) {
            // Text displayed on the button
            Text("Payment")
        }
    }
}

fun RequestZalo(context: Context) {
    val OrderApi = CreateOrder()

    try {
        val ResJsonObject: JSONObject = OrderApi.createOrder("50000")
        val code = ResJsonObject.getString("return_code")

        if (code.equals("1")) {
            val Token = ResJsonObject.getString("zp_trans_token")
            ZaloPaySDK.getInstance().payOrder(context as Activity, Token, "demozpdk://app", object: PayOrderListener {
                override fun onPaymentSucceeded(p0: String?, p1: String?, p2: String?) {
                    TODO("Not yet implemented")
                }

                override fun onPaymentCanceled(p0: String?, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onPaymentError(p0: ZaloPayError?, p1: String?, p2: String?) {
                    TODO("Not yet implemented")
                }

            })
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}