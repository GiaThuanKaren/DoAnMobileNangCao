package com.example.standardblognote.services

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.ColumnScope
import androidx.core.app.NotificationCompat
import com.example.standardblognote.R
import kotlin.random.Random

class NotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showBasicNotification() {
        val notification = NotificationCompat.Builder(context, "Notion_Reminder")
            .setContentTitle("Instagram UI 2.0")
            .setContentText("Stories, feed, carousel")
            .setSmallIcon(R.drawable.imagecover6)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    fun showExpandableNotification() {
        val image = context.bitmapFromResource(R.drawable.imagecover6)

        val notification = NotificationCompat.Builder(context, "Notion_Reminder")
            .setContentTitle("Instagram UI 2.0")
            .setContentText("Stories, feed, carousel")
            .setSmallIcon(R.drawable.avatar)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(image)
            .setStyle(
                NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(image)
                    .bigLargeIcon(null as Bitmap?)
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    fun showExpandableLongText() {
        val notification = NotificationCompat.Builder(context, "Notion_Reminder")
            .setContentTitle("Instagram UI 2.0")
            .setContentText("Stories, feed, carousel")
            .setSmallIcon(R.drawable.avatar)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat
                    .BigTextStyle()
                    .bigText("Very big text")
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    fun showInboxStyleNotification() {
        val notification = NotificationCompat.Builder(context, "Notion_Reminder")
            .setContentTitle("Instagram UI 2.0")
            .setContentText("Stories, feed, carousel")
            .setSmallIcon(R.drawable.avatar)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat
                    .InboxStyle()
                    .addLine("Line 1")
                    .addLine("Line 2")
                    .addLine("Line 3")
                    .addLine("Line 4")
                    .addLine("Line 5")
                    .addLine("Line 6")
                    .addLine("Line 7")
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    fun showNotificationGroup() {
        val groupId = "water_group"
        val summaryId = 0

        val notification1 = NotificationCompat.Builder(context, "Notion_Reminder")
            .setContentTitle("Instagram UI 2.0")
            .setContentText("Stories, feed, carousel")
            .setSmallIcon(R.drawable.avatar)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat
                    .InboxStyle()
                    .addLine("Line 1")
            )
            .setAutoCancel(true)
            .setGroup(groupId)
            .build()

        val notification2 = NotificationCompat.Builder(context, "Notion_Reminder")
            .setContentTitle("Instagram UI 2.0")
            .setContentText("Stories, feed, carousel")
            .setSmallIcon(R.drawable.avatar)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat
                    .InboxStyle()
                    .addLine("Line 1")
                    .addLine("Line 2")
            )
            .setAutoCancel(true)
            .setGroup(groupId)
            .build()

        val summaryNotification = NotificationCompat.Builder(context, "Notion_Reminder")
            .setContentTitle("Instagram UI 2.0")
            .setContentText("Stories, feed, carousel")
            .setSmallIcon(R.drawable.avatar)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat
                    .InboxStyle()
                    .setSummaryText("Instagram")
                    .setBigContentTitle("Stories, feed, carousel")
            )
            .setAutoCancel(true)
            .setGroup(groupId)
            .setGroupSummary(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification1
        )
        notificationManager.notify(
            Random.nextInt(),
            notification2
        )
        notificationManager.notify(
            Random.nextInt(),
            summaryNotification
        )
    }

    private fun Context.bitmapFromResource(
        @DrawableRes resId: Int
    ) = BitmapFactory.decodeResource(
        resources,
        resId
    )
}