package com.example.standardblognote.ui.screen

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.standardblognote.services.momo.AppMoMoLib
import com.example.standardblognote.services.momo.MoMoParameterNamePayment
import org.json.JSONException
import org.json.JSONObject
import java.util.UUID

@Composable
fun PaymentScreen() {
    val context = LocalContext.current

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Development Environment")
            Text(text = "Merchant Code: CGV19072017")
            Text(text = "Merchant Name: CGV Cinemas")

            var amount by remember { mutableStateOf("10000") }
            var description by remember { mutableStateOf("Fast & Furious 8") }

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") }
            )

            Button(onClick = { requestPayment(context, amount, description) }) {
                Text("Pay with MoMo")
            }
        }
    }
}

private fun requestPayment(context: Context, amount: String, description: String) {
    AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT)
    AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN)

    val merchantName = "CGV Cinemas"
    val merchantCode = "CGV19072017"
    val merchantNameLabel = "Nhà cung cấp"
    val fee = "0"

    val eventValue = hashMapOf<String, Any>(
        MoMoParameterNamePayment.MERCHANT_NAME to merchantName,
        MoMoParameterNamePayment.MERCHANT_CODE to merchantCode,
        MoMoParameterNamePayment.AMOUNT to amount,
        MoMoParameterNamePayment.DESCRIPTION to description,
        MoMoParameterNamePayment.FEE to fee,
        MoMoParameterNamePayment.MERCHANT_NAME_LABEL to merchantNameLabel,
        MoMoParameterNamePayment.REQUEST_ID to "$merchantCode-${UUID.randomUUID()}",
        MoMoParameterNamePayment.PARTNER_CODE to "CGV19072017"
    )

    val objExtraData = JSONObject()
    try {
        objExtraData.put("site_code", "008")
        objExtraData.put("site_name", "CGV Cresent Mall")
        objExtraData.put("screen_code", 0)
        objExtraData.put("screen_name", "Special")
        objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3")
        objExtraData.put("movie_format", "2D")
        objExtraData.put("ticket", "{\"ticket\":{\"01\":{\"type\":\"std\",\"price\":110000,\"qty\":3}}}")
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    eventValue[MoMoParameterNamePayment.EXTRA_DATA] = objExtraData.toString()
    eventValue[MoMoParameterNamePayment.REQUEST_TYPE] = "payment"
    eventValue[MoMoParameterNamePayment.LANGUAGE] = "vi"
    eventValue[MoMoParameterNamePayment.EXTRA] = ""

    //Request momo app
    AppMoMoLib.getInstance().requestMoMoCallBack(context as Activity, eventValue)
}