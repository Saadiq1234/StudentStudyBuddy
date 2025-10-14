package com.studybuddy.app.reminders

import android.content.Context
import androidx.work.*
import com.studybuddy.app.notifications.NotificationHelper
import java.util.concurrent.TimeUnit

class ReminderScheduler {
    companion object {
        fun scheduleReminder(context: Context, reminder: ReminderEntity) {
            val delay = reminder.timeEpoch - System.currentTimeMillis()
            if (delay <= 0) return val data = Data.Builder()
                .putString("title", reminder.title)
                .build()
            val request = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }
}