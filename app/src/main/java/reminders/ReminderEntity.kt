package com.studybuddy.app.reminders

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val userId: String,
    val title: String,
    val timeEpoch: Long,
    val repeat: Boolean = false,
    val synced: Boolean = false
)