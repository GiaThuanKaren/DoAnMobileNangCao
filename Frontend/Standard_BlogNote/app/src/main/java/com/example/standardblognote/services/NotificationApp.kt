package com.example.standardblognote.services

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class NotificationApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val notificationChannel = NotificationChannel(
            "Notion_Reminder",
            "Notion Reminder Channel",
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationChannel.description = "A Notification Channel for Notion"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}