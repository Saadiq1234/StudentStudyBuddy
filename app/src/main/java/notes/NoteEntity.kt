package com.studybuddy.app.notes
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey
    val id: String = java.util.UUID.randomUUID().toString(),
    val userId: String,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val synced: Boolean = false
)
