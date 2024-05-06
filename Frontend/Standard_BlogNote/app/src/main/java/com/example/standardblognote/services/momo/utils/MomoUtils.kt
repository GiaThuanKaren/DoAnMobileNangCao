package com.example.standardblognote.services.momo.utils

import android.os.Build
import android.util.Base64
import java.io.UnsupportedEncodingException


object MoMoUtils {
    fun getIPAddress(useIPv4: Boolean): String {
        return "0.0.0.0"
    }

    fun encodeString(s: String): String {
        var data = ByteArray(0)
        try {
            data = s.toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } finally {
            return Base64.encodeToString(data, Base64.DEFAULT)
        }
    }

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    fun capitalize(s: String): String {
        if (s.isEmpty()) return ""
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first) + s.substring(1)
        }
    }

    fun getDeviceSoftwareVersion(): String {
        return Build.VERSION.SDK_INT.toString()
    }
}