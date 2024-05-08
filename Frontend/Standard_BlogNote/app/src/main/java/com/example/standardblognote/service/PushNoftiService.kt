package com.example.standardblognote.service

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.standardblognote.R
class PushNoftiService :FirebaseMessagingService(){
    override fun onNewToken(token: String) {
        Log.i("Created New Token ",token)
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {


        super.onMessageReceived(message)
        val titleNofti: String? = message.notification?.title
        val body : String ?  = message.notification?.body
        println("Title "+ titleNofti  + "Body "+ body)
        showNotification(
            titleNofti,
            body
        )
//         Respone to received Message
    }
    private fun showNotification(title: String?, body: String?) {
        // Create a notification channel (required for Android 8.0 and above)
        val channelId = "default_channel_id"
        val channelName = "Default Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(channelId, channelName, importance)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

        // Create the notification
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.bell)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)

        // Show the notification
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManagerCompat.notify(0, notificationBuilder.build())
    }
}