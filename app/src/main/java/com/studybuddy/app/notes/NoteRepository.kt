package com.studybuddy.app.notes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository(private val noteDao: NoteDao) {

    // Flow of notes filtered by userId
    fun getNotesForUserFlow(userId: String): Flow<List<NoteEntity>> =
        noteDao.getAllNotes().map { notes -> notes.filter { it.userId == userId } }

    // Insert note
    suspend fun insert(note: NoteEntity) = noteDao.insert(note)

    // Update note
    suspend fun update(note: NoteEntity) = noteDao.update(note)

    // Delete note
    suspend fun delete(note: NoteEntity) = noteDao.delete(note)

    // Get unsynced notes
    suspend fun getUnsyncedNotes(userId: String): List<NoteEntity> =
        noteDao.getUnsyncedNotes(userId)
}
