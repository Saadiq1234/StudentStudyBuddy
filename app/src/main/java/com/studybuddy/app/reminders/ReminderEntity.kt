package com.studybuddy.app.reminders

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,
    val timeEpoch: Long,
    val timestamp: Long = System.currentTimeMillis()
)
