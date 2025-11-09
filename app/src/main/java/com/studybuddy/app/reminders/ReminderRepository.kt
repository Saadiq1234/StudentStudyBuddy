package com.studybuddy.app.reminders

import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val reminderDao: ReminderDao) {

    fun getRemindersForUserFlow(userId: String): Flow<List<ReminderEntity>> {
        return reminderDao.getRemindersForUserFlow(userId)
    }

    suspend fun insert(reminder: ReminderEntity) {
        reminderDao.insert(reminder)
    }

    suspend fun delete(reminder: ReminderEntity) {
        reminderDao.delete(reminder)
    }
}
