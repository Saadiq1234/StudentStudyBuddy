package com.studybuddy.app.notifications
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.studybuddy.app.R

object NotificationHelper {
    const val CHANNEL_ID = "study_reminders"
    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Study reminders"
            val descriptionText = "Reminders for study sessions"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val nm: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }

    fun showNotification(context: Context, title: String, body: String, id: Int = (System.currentTimeMillis() % 10000).toInt()) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo) .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            notify(id, builder.build())
        }
    }
}