package com.example.standardblognote.services.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "MyBroadcastReceiverAction") {
            Log.d("ALARM", "RECEIVED")
        }
    }
}