package com.example.standardblognote.ui.screen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.standardblognote.model.zalopayment.Api.CreateOrder
import com.example.standardblognote.model.zalopayment.Constant.AppInfo
import org.json.JSONObject
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener


//, View.OnClickListener
//@Composable
//class PaymentScreen : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            StandardBlogNoteTheme{
//                Surface(
//                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
//                ){
//                    MyContent2()
//                }
//            }
//
//
//        }
//
//    }
//
//    private fun requestZaloPay(){
//        var token : String=""
//        ZaloPaySDK.getInstance().payOrder(this, token, "demozpdk://app", object : PayOrderListener {
//            override fun onPaymentSucceeded(transactionId: String, transToken: String, appTransID: String) {
//
//            }
//
//            override fun onPaymentCanceled(zpTransToken: String, appTransID: String) {
//
//            }
//
//            override fun onPaymentError(
//                zaloPayError: ZaloPayError?,
//                zpTransToken: String,
//                appTransID: String
//            ) {
//
//            }
//        })
//
//    }
//
//
//
//}
internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}

@Composable
fun PaymentScreen(navController: NavHostController) {
//    LocalContext.current as Activity
//    val context =LocalContext.current

    val context = LocalContext.current
    val activity = context.findActivity()
    val policy = ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)
    ZaloPaySDK.init(2553, Environment.SANDBOX);



    fun requestZaloPay(){
        val orderApi = CreateOrder()
        val data = orderApi.createOrder("10000")
        val code:String?  = data?.getString("return_code")
        println("Payo")
        println(data)

        if ( code.equals("1")){
            var token = data!!.getString("zp_trans_token")
            Log.i("Token Payment ", token)
            ZaloPaySDK.getInstance().payOrder(activity, token, "demozpdk://app", object : PayOrderListener {
                override fun onPaymentSucceeded(transactionId: String, transToken: String, appTransID: String) {
                    Log.i("Succeeded Payment Zalo ",transactionId)
                }

                override fun onPaymentCanceled(zpTransToken: String,     appTransID: String) {
                    Log.i("Canceled Payment Zalo ",zpTransToken)
                }

                override fun onPaymentError(
                    zaloPayError: ZaloPayError?,
                    zpTransToken: String,
                    appTransID: String
                ) {
                    Log.i("Error Payment Zalo ", zaloPayError.toString())
                }
            })
        }









    }


    Button(onClick = {

        requestZaloPay()
    }) {
        Text("Updare Account To Premium")
    }






}



//private fun requestPayment(context: Context, amount: String, description: String) {
//    AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT)
//    AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN)
//
//    val merchantName = "CGV Cinemas"
//    val merchantCode = "CGV19072017"
//    val merchantNameLabel = "Nhà cung cấp"
//    val fee = "0"
//
//    val eventValue = hashMapOf<String, Any>(
//        MoMoParameterNamePayment.MERCHANT_NAME to merchantName,
//        MoMoParameterNamePayment.MERCHANT_CODE to merchantCode,
//        MoMoParameterNamePayment.AMOUNT to amount,
//        MoMoParameterNamePayment.DESCRIPTION to description,
//        MoMoParameterNamePayment.FEE to fee,
//        MoMoParameterNamePayment.MERCHANT_NAME_LABEL to merchantNameLabel,
//        MoMoParameterNamePayment.REQUEST_ID to "$merchantCode-${UUID.randomUUID()}",
//        MoMoParameterNamePayment.PARTNER_CODE to "CGV19072017"
//    )
//
//    val objExtraData = JSONObject()
//    try {
//        objExtraData.put("site_code", "008")
//        objExtraData.put("site_name", "CGV Cresent Mall")
//        objExtraData.put("screen_code", 0)
//        objExtraData.put("screen_name", "Special")
//        objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3")
//        objExtraData.put("movie_format", "2D")
//        objExtraData.put("ticket", "{\"ticket\":{\"01\":{\"type\":\"std\",\"price\":110000,\"qty\":3}}}")
//    } catch (e: JSONException) {
//        e.printStackTrace()
//    }
//    eventValue[MoMoParameterNamePayment.EXTRA_DATA] = objExtraData.toString()
//    eventValue[MoMoParameterNamePayment.REQUEST_TYPE] = "payment"
//    eventValue[MoMoParameterNamePayment.LANGUAGE] = "vi"
//    eventValue[MoMoParameterNamePayment.EXTRA] = ""
//
//    //Request momo app
//    AppMoMoLib.getInstance().requestMoMoCallBack(context as Activity, eventValue)
//}