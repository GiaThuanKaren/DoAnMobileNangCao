package com.example.standardblognote.services.momo

import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.example.standardblognote.services.momo.utils.MoMoConfig
import com.example.standardblognote.services.momo.utils.MoMoUtils
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class AppMoMoLib {
    private var action = ""
    private var environment = 0
    private var actionType = ""
    private var dataRequest: JSONObject? = null

    private var instance: AppMoMoLib? = null
    val REQUEST_CODE_MOMO = 1000

    companion object {
        @Volatile
        private var instance: AppMoMoLib? = null

        fun getInstance(): AppMoMoLib {
            if (instance == null)
                instance = AppMoMoLib()
            return instance!!
        }
    }

    fun setAction(_action: ACTION): String {
        action = if (_action == ACTION.MAP) {
            MoMoConfig.ACTION_SDK
        } else {
            MoMoConfig.ACTION_PAYMENT
        }
        return action
    }

    fun setActionType(_actionType: ACTION_TYPE): String {
        actionType = if (_actionType == ACTION_TYPE.GET_TOKEN) {
            MoMoConfig.ACTION_TYPE_GET_TOKEN
        } else {
            MoMoConfig.ACTION_TYPE_LINK
        }
        return actionType
    }

    fun setEnvironment(_environment: ENVIRONMENT): Int {
        environment = when (_environment) {
            ENVIRONMENT.DEBUG -> MoMoConfig.ENVIRONMENT_DEBUG
            ENVIRONMENT.DEVELOPMENT -> MoMoConfig.ENVIRONMENT_DEVELOPER
            ENVIRONMENT.PRODUCTION -> MoMoConfig.ENVIRONMENT_PRODUCTION
        }
        return environment
    }

    fun requestMoMoCallBack(activity: Activity, hashMap: Map<String, Any>) {
        val jsonData = JSONObject()
        val iterator = hashMap.keys.iterator()
        try {
            while (iterator.hasNext()) {
                val key = iterator.next()
                var value = hashMap[key]
                if (key == MoMoParameterNamePayment.EXTRA_DATA && value != null) {
                    value = MoMoUtils.encodeString(value.toString())
                }
                if (key == MoMoParameterNamePayment.EXTRA && value != null) {
                    value = MoMoUtils.encodeString(value.toString())
                }
                jsonData.put(key, value)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        dataRequest = jsonData
        if (action.isEmpty()) {
            Toast.makeText(activity, "Please init AppMoMoLib.getInstance().setAction", Toast.LENGTH_LONG).show()
            return
        }
        if (hashMap.isEmpty()) {
            Toast.makeText(activity, "Please set data after request", Toast.LENGTH_LONG).show()
            return
        }
        if (actionType.isEmpty()) {
            Toast.makeText(activity, "Please init AppMoMoLib.getInstance().setActionType", Toast.LENGTH_LONG).show()
            return
        } else {
            if ((action == MoMoConfig.ACTION_SDK && actionType != MoMoConfig.ACTION_TYPE_LINK) ||
                (action == MoMoConfig.ACTION_PAYMENT && actionType != MoMoConfig.ACTION_TYPE_GET_TOKEN)
            ) {
                Toast.makeText(activity, "Please set action type and action", Toast.LENGTH_LONG).show()
                return
            }
        }
        try {
            val packageClass: String = when (environment) {
                MoMoConfig.ENVIRONMENT_DEVELOPER -> MoMoConfig.MOMO_APP_PAKAGE_CLASS_DEVELOPER
                MoMoConfig.ENVIRONMENT_PRODUCTION -> MoMoConfig.MOMO_APP_PAKAGE_CLASS_PRODUCTION
                else -> MoMoConfig.MOMO_APP_PAKAGE_CLASS_DEBUG
            }

            val intent = Intent()
            val appName: String
            val applicationInfo: ApplicationInfo = activity.applicationContext.applicationInfo
            val stringId = applicationInfo.labelRes
            appName = if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else activity.applicationContext.getString(stringId)

            //put data to json object
            jsonData.put("sdkversion", "3.0.1")
            jsonData.put("clientIp", MoMoUtils.getIPAddress(true))
            jsonData.put("appname", appName)
            jsonData.put("packagename", activity.packageName)
            jsonData.put("action", actionType)
            jsonData.put("clientos", "Android_${MoMoUtils.getDeviceName()}_${MoMoUtils.getDeviceSoftwareVersion()}")

            if (appInstalledOrNot(activity, packageClass)) {
                intent.action = action
                intent.putExtra("JSON_PARAM", jsonData.toString())
                activity.startActivityForResult(intent, REQUEST_CODE_MOMO)
            } else {
                handleCallGooglePlay(activity, MoMoConfig.MOMO_APP_PAKAGE_STORE_DOWNLOAD)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun appInstalledOrNot(mActivity: Activity, uri: String): Boolean {
        val pm: PackageManager = mActivity.packageManager
        return try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun handleCallGooglePlay(mActivity: Activity, packageClass: String) {
        try {
            mActivity.startActivity(Intent("android.intent.action.VIEW", Uri.parse("market://details?id=$packageClass")))
        } catch (var4: Exception) {
            mActivity.startActivity(Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=${MoMoConfig.MOMO_APP_PAKAGE_CLASS_PRODUCTION}")))
        }
    }

    enum class ACTION {
        MAP,
        PAYMENT
    }

    enum class ENVIRONMENT {
        DEBUG,
        DEVELOPMENT,
        PRODUCTION
    }

    enum class ACTION_TYPE {
        GET_TOKEN,
        LINK
    }
}
