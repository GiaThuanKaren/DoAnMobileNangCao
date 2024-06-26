package com.example.standardblognote.model.zalopayment.Helper;

import android.annotation.SuppressLint
import com.example.standardblognote.model.zalopayment.Helper.HMac.HMacUtil
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects


class Helpers {
    private var transIdDefault = 1

    @SuppressLint("DefaultLocale")
    fun getAppTransId(): String {
        if (transIdDefault >= 100000) {
            transIdDefault = 1
        }
        transIdDefault += 1
        @SuppressLint("SimpleDateFormat") val formatDateTime : SimpleDateFormat = SimpleDateFormat("yyMMdd_hhmmss")
        val timeString: String = formatDateTime.format(Date())
        return String.format("%s%06d", timeString, transIdDefault)
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
    fun getMac(key: String, data: String): String {
        return Objects.requireNonNull<String>(
            HMacUtil().HMacHexStringEncode(
                HMacUtil().HMACSHA256,
                key,
                data
            )
        )
    }

}