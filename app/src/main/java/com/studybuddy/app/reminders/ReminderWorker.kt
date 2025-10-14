package com.studybuddy.app.reminders

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.studybuddy.app.notifications.NotificationHelper

class ReminderWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val title = inputData.getString("title") ?: "Study Reminder"
        NotificationHelper.showNotification(applicationContext, "Reminder", title)
        return Result.success()
    }
}