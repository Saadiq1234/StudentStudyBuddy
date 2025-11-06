package com.studybuddy.app.reminders

import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDao,
    private val userId: String
) {

    // Returns a Flow of reminders for this user
    fun getRemindersFlow(): Flow<List<ReminderEntity>> = reminderDao.getRemindersForUser(userId)

    suspend fun insert(reminder: ReminderEntity) {
        reminderDao.insert(reminder) // REPLACE ensures UI auto-updates
    }

    suspend fun delete(reminder: ReminderEntity) {
        reminderDao.delete(reminder)
    }

    suspend fun update(reminder: ReminderEntity) {
        reminderDao.update(reminder)
    }
}
