package com.studybuddy.app.notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // Flow for observing notes in real-time
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    // Suspend function for one-time fetching of notes
    @Query("SELECT * FROM notes")
    suspend fun getAllNotesSuspend(): List<NoteEntity>

    // Fetch only unsynced notes for a specific user
    @Query("SELECT * FROM notes WHERE userId = :userId AND synced = 0")
    suspend fun getUnsyncedNotes(userId: String): List<NoteEntity>

    @Insert
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)
}
